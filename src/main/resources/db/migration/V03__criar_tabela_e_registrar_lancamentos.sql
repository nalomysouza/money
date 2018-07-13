CREATE TABLE lancamento (
	id BIGSERIAL PRIMARY KEY,
    valor NUMERIC(10,2) NOT NULL,
    data_vencimento DATE NOT NULL,
    data_pagamento DATE NOT NULL,
    descricao VARCHAR(50) NOT NULL,
    observacao VARCHAR(100),
    tipo_lancamento VARCHAR(20) NOT NULL,
    id_categoria BIGINT NOT NULL,
    id_pessoa BIGINT NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id),
    FOREIGN KEY (id_pessoa) REFERENCES pessoa(id)
);

INSERT INTO lancamento (valor, data_vencimento, data_pagamento, descricao, observacao, tipo_lancamento, id_categoria, id_pessoa) VALUES 
('100.20','2017-02-01','2017-02-01','Gás de Cozinha','+ brinde pano de prato','DESPESA',2 ,1);
