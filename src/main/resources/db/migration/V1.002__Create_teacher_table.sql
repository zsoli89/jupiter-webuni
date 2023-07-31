create sequence teacher_seq start with 1 increment by 50;
create table teacher (id integer not null, birthdate date, name varchar(255), primary key (id));
