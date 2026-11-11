package com.github.caiqueBraga12099.cidadesegura.atendimentos.client;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.client.dto.*;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.exception.RegraNegocioException;

@Component
public class OcorrenciaFallbackFactory implements FallbackFactory<OcorrenciaClient> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OcorrenciaFallbackFactory.class);

    @Override
    public OcorrenciaClient create(Throwable cause) {
        LOGGER.warn("Falha na comunicacao com o servico de ocorrencias", cause);
        return new OcorrenciaClient() {
            @Override
            public OcorrenciaResponseDTO buscar(Long id) {
                throw traduzir(cause);
            }

            @Override
            public OcorrenciaResponseDTO alterarStatus(Long id, OcorrenciaStatusRequestDTO request) {
                throw traduzir(cause);
            }
        };
    }

    private RegraNegocioException traduzir(Throwable cause) {
        Throwable atual = cause;
        while (atual != null) {
            if (atual instanceof FeignException erro) {
                if (erro.status() == 404) {
                    return new RegraNegocioException(404, "Ocorrencia nao encontrada no servico de ocorrencias.");
                }
                if (erro.status() == 409) {
                    return new RegraNegocioException(409, "A ocorrencia nao permite esta operacao. Consulte seu status.");
                }
                if (erro.status() == 400) {
                    return new RegraNegocioException(400, "Dados rejeitados pelo servico de ocorrencias.");
                }
            }
            atual = atual.getCause();
        }
        return new RegraNegocioException(503,
                "Servico de ocorrencias indisponivel. Nenhum sucesso foi confirmado; tente novamente a mesma operacao.");
    }
}
