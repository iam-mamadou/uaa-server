drop table if exists user;
create table user(
    user_id integer primary key auto_increment,
    first_name varchar(255),
    last_name varchar(255),
    username varchar(255),
    email varchar(255),
    password varchar(255),
    enabled boolean,
    locked boolean,
    expired boolean
);