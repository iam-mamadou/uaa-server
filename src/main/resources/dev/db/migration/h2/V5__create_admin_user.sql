insert into user(user_id,first_name,last_name,username,password,email,enabled,expired,locked)
values(1,'first','last','admin','$2a$10$Noxuv0dfNT.O68B4hgginevx4MhTCR//r3hOKbMGN6AIhJPa4na7.','admin@test.com',true,false,false);

insert into user_authority(user_id, role)
values(1,'ROLE_ADMIN')