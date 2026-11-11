CREATE TABLE ocorrencias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(120) NOT NULL,
    descricao VARCHAR(1000) NOT NULL,
    endereco VARCHAR(200) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    status VARCHAR(30) NOT NULL,
    data_criacao TIMESTAMP(6) NOT NULL,
    versao BIGINT NOT NULL DEFAULT 0,
    referencia_atendimento VARCHAR(36),
    CONSTRAINT ck_ocorrencia_status CHECK (status IN ('ABERTA', 'EM_ATENDIMENTO', 'RESOLVIDA'))
);
