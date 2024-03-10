create table 'doctor' (
	email varchar2(255 char) not null,
	password varchar2(255 char),
	name varchar2(255 char),
	degree varchar2(255 char),
	specialization varchar2(255 char),
	city varchar2(255 char),
	state varchar2(255 char),
	primary key (email)
	)