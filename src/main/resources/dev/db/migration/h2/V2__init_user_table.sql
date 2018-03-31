drop table if exists user;
create table user(
    user_id int not null auto_increment primary key,
    first_name varchar(255),
    last_name varchar(255),
    username varchar(255),
    email varchar(255),
    password varchar(255),
    enabled boolean,
    locked boolean,
    expired boolean
);