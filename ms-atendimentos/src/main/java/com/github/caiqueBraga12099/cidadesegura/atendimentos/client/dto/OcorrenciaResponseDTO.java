package com.github.caiqueBraga12099.cidadesegura.atendimentos.client.dto;




public class OcorrenciaResponseDTO {

    private Long id;

    private String status;

    public OcorrenciaResponseDTO() {
    }

    public OcorrenciaResponseDTO(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
