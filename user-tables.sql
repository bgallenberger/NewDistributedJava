create table USERS(
    username varchar(50) not null constraint user_pk primary key,
    password varchar(60) not null,
    enabled smallint default 1,
    first_name varchar(50),
    last_name varchar(50),
    email varchar(50)
);

create table AUTHORITIES (
    username varchar(50) not null references USERS (username),
    authority varchar(50) not null
);

-- 1 means enabled is true
-- 0 means enabled is false
-- default is 1 (per table column definition)

insert into USERS (username, password, enabled, first_name, last_name, email)
values ('bgallenberger', 'secret', 1, 'brian', 'gallenberger', 'bgallenberger@wctc.edu');

insert into USERS (username, password, first_name, last_name, email)
values ('stacy', 'p', 'Stacy', 'Read', 'sread@wctc.edu');

insert into AUTHORITIES values ('bgallenberger', 'USER');
insert into AUTHORITIES values ('stacy', 'USER');
insert into AUTHORITIES values ('stacy', 'ADMIN');

update USERS set password = '$2a$10$wvOx0cpDYFkimT9N7Na4COTR2OPtU6ci69cDQJTqC5sRJ58ra76kG'
where password = 'p';