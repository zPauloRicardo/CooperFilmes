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

