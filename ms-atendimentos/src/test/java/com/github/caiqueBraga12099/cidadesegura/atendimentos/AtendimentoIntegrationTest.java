package com.github.caiqueBraga12099.cidadesegura.atendimentos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.service.AtendimentoService;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.dto.request.*;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.entity.StatusAtendimento;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.exception.RegraNegocioException;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.client.*;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.client.dto.OcorrenciaResponseDTO;

@SpringBootTest
@ActiveProfiles("test")
class AtendimentoIntegrationTest {

    @Autowired
    private AtendimentoService service;

    @MockitoBean
    private OcorrenciaClient client;

    @Test
    void migrationsEStatusChamamOcorrenciaAntesDeConfirmar() {
        assertThat(service.findAll()).hasSizeGreaterThanOrEqualTo(2);
        when(client.buscar(50L)).thenReturn(new OcorrenciaResponseDTO(50L, "ABERTA"));
        var criado = service.create(new AtendimentoRequestDTO(50L, "Equipe teste", "Ana Silva", "Vistoria prevista"));
        service.alterarStatus(criado.getId(), new StatusRequestDTO(StatusAtendimento.EM_ANDAMENTO));
        verify(client).alterarStatus(eq(50L), argThat(request -> "EM_ATENDIMENTO".equals(request.getStatus())));
        service.alterarStatus(criado.getId(), new StatusRequestDTO(StatusAtendimento.CONCLUIDO));
        verify(client).alterarStatus(eq(50L), argThat(request -> "RESOLVIDA".equals(request.getStatus())));
        assertThat(service.findById(criado.getId()).getStatus()).isEqualTo(StatusAtendimento.CONCLUIDO);
    }

    @Test
    void indisponibilidadeNaoConfirmaStatusLocal() {
        when(client.buscar(51L)).thenReturn(new OcorrenciaResponseDTO(51L, "ABERTA"));
        var criado = service.create(new AtendimentoRequestDTO(51L, "Equipe teste", "Ana Silva", "Vistoria prevista"));
        when(client.alterarStatus(eq(51L), any())).thenThrow(new RegraNegocioException(503, "Indisponivel"));
        assertThatThrownBy(() -> service.alterarStatus(criado.getId(), new StatusRequestDTO(StatusAtendimento.EM_ANDAMENTO)))
                .isInstanceOf(RegraNegocioException.class);
        assertThat(service.findById(criado.getId()).getStatus()).isEqualTo(StatusAtendimento.PLANEJADO);
    }

    @Test
    void naoPermitePularEtapasOuDuplicarAtendimento() {
        when(client.buscar(52L)).thenReturn(new OcorrenciaResponseDTO(52L, "ABERTA"));
        var request = new AtendimentoRequestDTO(52L, "Equipe teste", "Ana Silva", "Vistoria prevista");
        var criado = service.create(request);
        assertThatThrownBy(() -> service.alterarStatus(criado.getId(), new StatusRequestDTO(StatusAtendimento.CONCLUIDO)))
                .isInstanceOf(RegraNegocioException.class);
        verify(client, never()).alterarStatus(anyLong(), any());
        assertThatThrownBy(() -> service.create(request)).isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class);
        service.delete(criado.getId());
        assertThatThrownBy(() -> service.findById(criado.getId())).isInstanceOf(RegraNegocioException.class);
    }

    @Test
    void fallbackRetorna503SemInventarResposta() {
        var fallback = new OcorrenciaFallbackFactory().create(new RuntimeException("timeout"));
        assertThatThrownBy(() -> fallback.buscar(1L))
                .isInstanceOfSatisfying(RegraNegocioException.class, erro -> assertThat(erro.getStatus()).isEqualTo(503));
    }
}
