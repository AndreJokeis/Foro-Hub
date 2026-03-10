insert into usuarios (nombre, correo, contrasena)
	values ('Chuy', 'jesus@gmail.com', '$2a$10$pXbbDoV79z/NANxFDFxeseQ2h5tCYzu6o.7dBBSMylnXJsNktOB.e'),
	('Richy', 'richyms@gmail.com', '$2a$10$pXbbDoV79z/NANxFDFxeseQ2h5tCYzu6o.7dBBSMylnXJsNktOB.e'),
	('Andre', 'garciajoseph537@gmail.com', '$2a$10$pXbbDoV79z/NANxFDFxeseQ2h5tCYzu6o.7dBBSMylnXJsNktOB.e');

insert into cursos (nombre, categoria)
	values
		('Spring Boot 3', 'BACKEND'),
        ('MySQL para estudiantes', 'BASES_DE_DATOS');

insert into perfiles (nombre)
	values
		('ADMIN'),
        ('USUARIO'),
        ('MODERADOR');


insert into usuario_perfil (usuario_id,perfil_id)
	values
		(1, 2),
        (1, 3),
        (3, 1),
        (3, 2),
        (3, 3),
        (2, 2);