create sequence if not exists  course_seq start with 1 increment by 50;
create table course (id bigint not null, name varchar(255), primary key (id));
create table course_students (courses_id bigint not null, students_id bigint not null, primary key (courses_id, students_id));
create table course_teachers (courses_id bigint not null, teachers_id bigint not null, primary key (courses_id, teachers_id));
alter table if exists course_students add constraint FK532dg5ikp3dvbrbiiqefdoe6m foreign key (students_id) references student;
alter table if exists course_students add constraint FKh6irfl8rj4jgb3xrlyxsr2bdk foreign key (courses_id) references course;
alter table if exists course_teachers add constraint FKe3n62rwx3uc1yucgkmw6gjkm5 foreign key (teachers_id) references teacher;
alter table if exists course_teachers add constraint FK5ntgqv47cbgq8s0la1myeg5ly foreign key (courses_id) references course;