package com.github.caiqueBraga12099.cidadesegura.ocorrencias.exception;



public class RegraNegocioException extends RuntimeException {

    private final int status;

    public RegraNegocioException(int status, String mensagem) {
        super(mensagem);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
