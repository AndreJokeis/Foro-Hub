create table cursos(
    id bigint primary key auto_increment,
    nombre varchar(100) not null unique,
    categoria varchar(50) not null
);

create table perfiles(
    id bigint primary key auto_increment,
    nombre varchar(50) not null,
    constraint uk_perfiles_nombre unique (nombre)
);

create table usuarios(
    id bigint primary key auto_increment,
    nombre varchar(100) not null,
    correo varchar(100) not null,
    contrasena varchar(255) not null,
    constraint uk_usuarios_correo unique (correo)
);

create table usuario_perfil(
    usuario_id bigint not null,
    perfil_id bigint not null,

    primary key(usuario_id, perfil_id),
    constraint fk_usuarioperfil_usuario foreign key(usuario_id) references usuarios(id),
    constraint fk_usuarioperfil_perfil foreign key(perfil_id) references perfiles(id)
);

create table topicos(
    id bigint primary key auto_increment,
    titulo varchar(100) not null,
    mensaje TEXT not null,
    activo tinyint not null,
    fecha_de_creacion datetime not null,
    status varchar(20) not null,
    usuario_id bigint not null,
    curso_id bigint not null,

    constraint fk_topico_usuario foreign key(usuario_id) references usuarios(id),
    constraint fk_topico_curso foreign key(curso_id) references cursos(id)
);

create table respuestas(
    id bigint primary key auto_increment,
    mensaje TEXT not null,
    fecha_de_creacion datetime not null,
    solucion tinyint default 0,
    topico_id bigint not null,
    usuario_id bigint not null,

    constraint fk_respuesta_topico foreign key(topico_id) references topicos(id),
    constraint fk_respuesta_usuario foreign key(usuario_id) references usuarios(id)
);