CREATE TABLE public.vacina
(
    codigo bigserial NOT NULL,
    nome text,
    descricao text,
    status text DEFAULT 'ATIVO',
    PRIMARY KEY (codigo)
);