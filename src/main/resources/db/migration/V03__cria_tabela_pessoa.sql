CREATE TABLE public.pessoa
(
    codigo serial NOT NULL,
    nome text,
    cpf text,
    data_nascimento date,
    codigo_profissao text,
    status text DEFAULT 'ATIVO',
    PRIMARY KEY (codigo)
);