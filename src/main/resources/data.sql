--insert users
insert into user(email, password, name)
values ('admin@admin.admin', '$2a$10$RBq4e6nSLa0BbsILq7uile6i27EhAN/UAJlGow8PRJK17s30sXmX2', 'admin name');
insert into user(email, password, name)
values ('basic@basic.basic', '$2a$10$RBq4e6nSLa0BbsILq7uile6i27EhAN/UAJlGow8PRJK17s30sXmX2', 'basic name');

insert into profile(name)
values('ADMIN');
insert into profile(name)
values('BASIC');

insert into user_x_profile(user_id, profile_id)
values(1, 1);
insert into user_x_profile(user_id, profile_id)
values(2, 2);