CREATE TABLE pessoa (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    ativo BOOLEAN NOT NULL,
    logradouro VARCHAR(150),
    numero VARCHAR(8),
    complemento VARCHAR(200),
    bairro VARCHAR(150),
    cep VARCHAR(8),
    cidade VARCHAR(50),
    estado VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;