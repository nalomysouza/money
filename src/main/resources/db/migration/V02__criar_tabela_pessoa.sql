CREATE TABLE pessoa (
	id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    ativo boolean,
    logradouro VARCHAR(150) NOT NULL,
    numero VARCHAR(8),
    complemento VARCHAR(50),
    bairro VARCHAR(40),
	cep VARCHAR(10),
	cidade VARCHAR(30),
	estado VARCHAR(25)
);

INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) VALUES 
('Nalomy',true,'Pedro Sampaio','250','BL 2 AP 101','Passaré','60861-500','Fortaleza','Ceará');
INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) VALUES 
('Amanda',true,'Pedro Sampaio','250','BL 2 AP 101','Passaré','60861-500','Fortaleza','Ceará');
