package com.github.caiqueBraga12099.cidadesegura.atendimentos.dto.request;

import com.github.caiqueBraga12099.cidadesegura.atendimentos.entity.StatusAtendimento;
import jakarta.validation.constraints.NotNull;


public class StatusRequestDTO {

    @NotNull
    private StatusAtendimento status;

    public StatusRequestDTO() {
    }

    public StatusRequestDTO(StatusAtendimento status) {
        this.status = status;
    }

    public StatusAtendimento getStatus() {
        return status;
    }

    public void setStatus(StatusAtendimento status) {
        this.status = status;
    }
}
