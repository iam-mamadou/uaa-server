drop table if exists authority;
create table authority(
    role varchar(255) primary key
);
insert into authority(role) values('ROLE_ADMIN');
insert into authority(role) values('ROLE_USER');
