CREATE TABLE public.pessoa
(
    codigo bigserial NOT NULL,
    nome text,
    cpf text,
    data_nascimento timestamp,
    profissao text, 
    PRIMARY KEY (codigo)
);