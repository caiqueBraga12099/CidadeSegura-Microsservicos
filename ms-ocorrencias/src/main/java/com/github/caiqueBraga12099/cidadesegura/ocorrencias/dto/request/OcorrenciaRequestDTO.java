package com.github.caiqueBraga12099.cidadesegura.ocorrencias.dto.request;

import jakarta.validation.constraints.*;


public class OcorrenciaRequestDTO {

    @NotBlank(message = "Campo obrigatorio")
    @Size(min = 3, max = 120, message = "Use entre 3 e 120 caracteres")
    private String titulo;

    @NotBlank(message = "Campo obrigatorio")
    @Size(min = 3, max = 1000, message = "Use entre 3 e 1000 caracteres")
    private String descricao;

    @NotBlank(message = "Campo obrigatorio")
    @Size(min = 3, max = 200, message = "Use entre 3 e 200 caracteres")
    private String endereco;

    @NotBlank(message = "Campo obrigatorio")
    @Size(min = 3, max = 100, message = "Use entre 3 e 100 caracteres")
    private String bairro;

    public OcorrenciaRequestDTO() {
    }

    public OcorrenciaRequestDTO(String titulo, String descricao, String endereco, String bairro) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.endereco = endereco;
        this.bairro = bairro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
}
