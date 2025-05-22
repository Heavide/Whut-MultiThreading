#create users

create table if not exists users(
	name varchar(255) not null default '',
	password varchar(32) not null default '',
	role varchar(16) not null default '',
	primary key(`name`)
);

#init users

insert into users(name, password, role)
values
	('A', '123', 'Administrator'),
	('B', '123', 'Browser'),
	('O', '123', 'Operator');


#create docs

create table if not exists docs(
	id varchar(255) not null default '',
	creator varchar(255) not null default '',
	timestamp datetime not null default '2023-01-01 00:00:00',
	description text,
	filename varchar(255) not null default '', 
	primary key(`id`)
);

#init docs

insert into docs values('0001', 'O', '2023-01-01 00:00:00', 'test', 'test.doc');