create table cf_clientes
(
    id            uuid         not null primary key,
    nome          varchar(255) not null,
    email         varchar(255) not null
        unique,
    telefone      varchar(255),
    data_cadastro timestamp(6)
);

alter table cf_clientes owner to cooperfilmes;

create table cf_roteiros
(
    id            uuid         not null
        primary key,
    id_cliente    uuid         not null
        constraint fkgjva2rfsn8a2qpucfcidvo3tw
            references cf_clientes,
    texto         varchar(255) not null,
    ultima_etapa  integer,
    data_cadastro timestamp(6)
);

alter table cf_roteiros owner to cooperfilmes;

create table cf_etapa_roteiros
(
    id            uuid not null
        primary key,
    id_roteiro    uuid not null
        constraint fkmsw91dgoio3n7gvqi1ws4ffe7
            references cf_roteiros,
    tipo_etapa    integer,
    justificativa varchar(255),
    data_etapa    timestamp(6),
    id_usuario    uuid,
    nome_usuario  varchar(255)
);

alter table cf_etapa_roteiros owner to cooperfilmes;

create table cf_usuarios
(
    id            uuid         not null
        primary key,
    nome          varchar(255) not null,
    email         varchar(255) not null
        unique,

    perfil        varchar(255) not null
        unique,
    senha         varchar(255),
    data_cadastro timestamp(6)
);

alter table cf_usuarios owner to cooperfilmes;

create table cf_voto_roteiros
(
    id           uuid not null
        primary key,
    id_roteiro   uuid not null
        constraint fklt8h1hj9hxddw48i78jje8ayd
            references cf_roteiros,
    tipo_voto    varchar(255),
    id_usuario   uuid,
    nome_usuario varchar(255),
    data_voto    timestamp(6)
);

alter table cf_voto_roteiros owner to cooperfilmes;


INSERT INTO cf_usuarios (id, nome, email, perfil, senha, data_cadastro) VALUES ('01fd4794-376a-4ddd-9b62-16881db3aab1', 'Revisor', 'revisor@paulojr.me', 'R', '$2a$10$ClP/XM2lqSRUAnPkae5My.1yrA0yhh.UEpifR/bMWNApggAWhsINe', '2024-09-15 23:49:52.000000');
INSERT INTO cf_usuarios (id, nome, email, perfil, senha, data_cadastro) VALUES ('39269eba-c772-4aac-aaca-156de27009e0', 'Aprovador', 'aprovador@paulojr.me', 'P', '$2a$10$ClP/XM2lqSRUAnPkae5My.1yrA0yhh.UEpifR/bMWNApggAWhsINe', '2024-09-15 23:49:52.000000');
INSERT INTO cf_usuarios (id, nome, email, perfil, senha, data_cadastro) VALUES ('94939151-b9d1-4b68-85ce-5e4797f25544', 'Analista', 'analista@paulojr.me', 'A', '$2a$10$ClP/XM2lqSRUAnPkae5My.1yrA0yhh.UEpifR/bMWNApggAWhsINe', '2024-09-15 23:49:52.000000');


