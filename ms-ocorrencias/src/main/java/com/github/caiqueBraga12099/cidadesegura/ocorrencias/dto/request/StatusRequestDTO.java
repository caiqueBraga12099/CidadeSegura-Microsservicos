package com.github.caiqueBraga12099.cidadesegura.ocorrencias.dto.request;

import com.github.caiqueBraga12099.cidadesegura.ocorrencias.entity.StatusOcorrencia;
import jakarta.validation.constraints.*;


public class StatusRequestDTO {

    @NotNull
    private StatusOcorrencia status;

    @NotBlank
    @Size(min = 36, max = 36)
    private String referenciaAtendimento;

    public StatusRequestDTO() {
    }

    public StatusRequestDTO(StatusOcorrencia status, String referenciaAtendimento) {
        this.status = status;
        this.referenciaAtendimento = referenciaAtendimento;
    }

    public StatusOcorrencia getStatus() {
        return status;
    }

    public void setStatus(StatusOcorrencia status) {
        this.status = status;
    }

    public String getReferenciaAtendimento() {
        return referenciaAtendimento;
    }

    public void setReferenciaAtendimento(String referenciaAtendimento) {
        this.referenciaAtendimento = referenciaAtendimento;
    }
}
