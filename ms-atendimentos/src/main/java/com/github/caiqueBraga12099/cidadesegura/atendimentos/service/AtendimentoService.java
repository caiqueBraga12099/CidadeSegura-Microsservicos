package com.github.caiqueBraga12099.cidadesegura.atendimentos.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.entity.*;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.dto.request.*;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.dto.response.AtendimentoResponseDTO;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.repository.AtendimentoRepository;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.mapper.AtendimentoMapper;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.client.OcorrenciaClient;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.client.dto.OcorrenciaStatusRequestDTO;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.exception.RegraNegocioException;

@Service
@Transactional
public class AtendimentoService {

    private final AtendimentoRepository repository;
    private final OcorrenciaClient ocorrenciaClient;

    public AtendimentoService(AtendimentoRepository repository, OcorrenciaClient ocorrenciaClient) {
        this.repository = repository;
        this.ocorrenciaClient = ocorrenciaClient;
    }

    @Transactional(readOnly = true)
    public List<AtendimentoResponseDTO> findAll() {
        return repository.findAll().stream().map(AtendimentoMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AtendimentoResponseDTO findById(Long id) {
        return AtendimentoMapper.toResponse(buscar(id));
    }

    public AtendimentoResponseDTO create(AtendimentoRequestDTO request) {
        var ocorrencia = ocorrenciaClient.buscar(request.getOcorrenciaId());
        if (!"ABERTA".equals(ocorrencia.getStatus())) {
            throw new RegraNegocioException(409, "Planeje atendimento apenas para ocorrencia aberta.");
        }
        Atendimento entity = new Atendimento();
        AtendimentoMapper.atualizar(entity, request);
        entity.setOcorrenciaId(request.getOcorrenciaId());
        entity.setReferencia(UUID.randomUUID().toString());
        entity.setStatus(StatusAtendimento.PLANEJADO);
        entity.setDataCriacao(LocalDateTime.now());
        return AtendimentoMapper.toResponse(repository.saveAndFlush(entity));
    }

    public AtendimentoResponseDTO update(Long id, AtendimentoRequestDTO request) {
        Atendimento entity = buscar(id);
        if (!entity.getOcorrenciaId().equals(request.getOcorrenciaId())) {
            throw new RegraNegocioException(409, "Nao e permitido trocar a ocorrencia de um atendimento.");
        }
        if (entity.getStatus() == StatusAtendimento.CONCLUIDO) {
            throw new RegraNegocioException(409, "Atendimento concluido nao pode ser editado.");
        }
        AtendimentoMapper.atualizar(entity, request);
        return AtendimentoMapper.toResponse(repository.saveAndFlush(entity));
    }

    public void delete(Long id) {
        Atendimento entity = buscar(id);
        if (entity.getStatus() != StatusAtendimento.PLANEJADO) {
            throw new RegraNegocioException(409, "Apenas atendimentos planejados podem ser excluidos.");
        }
        // Consulta remota evita apagar uma operacao iniciada cujo retorno se perdeu.
        var ocorrencia = ocorrenciaClient.buscar(entity.getOcorrenciaId());
        if (!"ABERTA".equals(ocorrencia.getStatus())) {
            throw new RegraNegocioException(409, "Ocorrencia ja iniciada. Repita a alteracao de status para reconciliar.");
        }
        repository.delete(entity);
    }

    public AtendimentoResponseDTO alterarStatus(Long id, StatusRequestDTO request) {
        Atendimento entity = buscar(id);
        StatusAtendimento atual = entity.getStatus();
        StatusAtendimento novo = request.getStatus();
        if (atual == novo) {
            return AtendimentoMapper.toResponse(entity);
        }
        boolean permitido = atual == StatusAtendimento.PLANEJADO && novo == StatusAtendimento.EM_ANDAMENTO
                || atual == StatusAtendimento.EM_ANDAMENTO && novo == StatusAtendimento.CONCLUIDO;
        if (!permitido) {
            throw new RegraNegocioException(409, "Transicao de status do atendimento nao permitida.");
        }
        String statusRemoto = novo == StatusAtendimento.EM_ANDAMENTO ? "EM_ATENDIMENTO" : "RESOLVIDA";
        ocorrenciaClient.alterarStatus(entity.getOcorrenciaId(),
                new OcorrenciaStatusRequestDTO(statusRemoto, entity.getReferencia()));
        entity.setStatus(novo);
        return AtendimentoMapper.toResponse(repository.saveAndFlush(entity));
    }

    private Atendimento buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException(404, "Atendimento nao encontrado."));
    }
}
