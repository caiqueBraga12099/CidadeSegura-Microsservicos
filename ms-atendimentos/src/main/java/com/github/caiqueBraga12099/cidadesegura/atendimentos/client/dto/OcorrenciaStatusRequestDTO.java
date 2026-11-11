package com.github.caiqueBraga12099.cidadesegura.atendimentos.client.dto;




public class OcorrenciaStatusRequestDTO {

    private String status;

    private String referenciaAtendimento;

    public OcorrenciaStatusRequestDTO() {
    }

    public OcorrenciaStatusRequestDTO(String status, String referenciaAtendimento) {
        this.status = status;
        this.referenciaAtendimento = referenciaAtendimento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReferenciaAtendimento() {
        return referenciaAtendimento;
    }

    public void setReferenciaAtendimento(String referenciaAtendimento) {
        this.referenciaAtendimento = referenciaAtendimento;
    }
}
