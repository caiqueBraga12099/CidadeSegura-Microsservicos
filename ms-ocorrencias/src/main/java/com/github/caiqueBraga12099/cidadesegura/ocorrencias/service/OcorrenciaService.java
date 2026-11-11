package com.github.caiqueBraga12099.cidadesegura.ocorrencias.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.caiqueBraga12099.cidadesegura.ocorrencias.entity.*;
import com.github.caiqueBraga12099.cidadesegura.ocorrencias.dto.request.*;
import com.github.caiqueBraga12099.cidadesegura.ocorrencias.dto.response.OcorrenciaResponseDTO;
import com.github.caiqueBraga12099.cidadesegura.ocorrencias.repository.OcorrenciaRepository;
import com.github.caiqueBraga12099.cidadesegura.ocorrencias.mapper.OcorrenciaMapper;
import com.github.caiqueBraga12099.cidadesegura.ocorrencias.exception.RegraNegocioException;

@Service
@Transactional
public class OcorrenciaService {

    private final OcorrenciaRepository repository;

    public OcorrenciaService(OcorrenciaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<OcorrenciaResponseDTO> findAll() {
        return repository.findAll().stream().map(OcorrenciaMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public OcorrenciaResponseDTO findById(Long id) {
        return OcorrenciaMapper.toResponse(buscar(id));
    }

    public OcorrenciaResponseDTO create(OcorrenciaRequestDTO request) {
        Ocorrencia entity = new Ocorrencia();
        OcorrenciaMapper.atualizar(entity, request);
        entity.setStatus(StatusOcorrencia.ABERTA);
        entity.setDataCriacao(LocalDateTime.now());
        return OcorrenciaMapper.toResponse(repository.saveAndFlush(entity));
    }

    public OcorrenciaResponseDTO update(Long id, OcorrenciaRequestDTO request) {
        Ocorrencia entity = buscar(id);
        if (entity.getStatus() == StatusOcorrencia.RESOLVIDA) {
            throw new RegraNegocioException(409, "Ocorrencia resolvida nao pode ser editada.");
        }
        OcorrenciaMapper.atualizar(entity, request);
        return OcorrenciaMapper.toResponse(repository.saveAndFlush(entity));
    }

    public void delete(Long id) {
        Ocorrencia entity = buscar(id);
        if (entity.getStatus() != StatusOcorrencia.ABERTA) {
            throw new RegraNegocioException(409, "Apenas ocorrencias abertas podem ser excluidas.");
        }
        repository.delete(entity);
    }

    public OcorrenciaResponseDTO alterarStatus(Long id, StatusRequestDTO request) {
        Ocorrencia entity = buscar(id);
        StatusOcorrencia atual = entity.getStatus();
        StatusOcorrencia novo = request.getStatus();
        String referencia = request.getReferenciaAtendimento();
        if (entity.getReferenciaAtendimento() != null
                && !entity.getReferenciaAtendimento().equals(referencia)) {
            throw new RegraNegocioException(409, "Ocorrencia vinculada a outro atendimento.");
        }
        if (atual == novo && referencia.equals(entity.getReferenciaAtendimento())) {
            return OcorrenciaMapper.toResponse(entity);
        }
        boolean permitido = atual == StatusOcorrencia.ABERTA && novo == StatusOcorrencia.EM_ATENDIMENTO
                || atual == StatusOcorrencia.EM_ATENDIMENTO && novo == StatusOcorrencia.RESOLVIDA;
        if (!permitido) {
            throw new RegraNegocioException(409, "Transicao de status da ocorrencia nao permitida.");
        }
        entity.setReferenciaAtendimento(referencia);
        entity.setStatus(novo);
        return OcorrenciaMapper.toResponse(repository.saveAndFlush(entity));
    }

    private Ocorrencia buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException(404, "Ocorrencia nao encontrada."));
    }
}
