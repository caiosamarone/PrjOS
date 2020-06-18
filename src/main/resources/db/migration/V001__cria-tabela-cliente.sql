create table cliente (
	id bigint not null auto_increment,
	nome varchar(30) not null,
	email varchar(50) not null,
	telefone varchar(20) not null,

primary key (id)
);