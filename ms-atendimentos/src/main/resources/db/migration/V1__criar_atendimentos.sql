CREATE TABLE atendimentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ocorrencia_id BIGINT NOT NULL UNIQUE,
    equipe VARCHAR(100) NOT NULL,
    responsavel VARCHAR(100) NOT NULL,
    observacao VARCHAR(1000) NOT NULL,
    status VARCHAR(30) NOT NULL,
    data_criacao TIMESTAMP(6) NOT NULL,
    versao BIGINT NOT NULL DEFAULT 0,
    referencia VARCHAR(36) NOT NULL UNIQUE,
    CONSTRAINT ck_atendimento_status CHECK (status IN ('PLANEJADO', 'EM_ANDAMENTO', 'CONCLUIDO'))
);
