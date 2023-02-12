create table admin
(
    username varchar(20) not null comment '管理员的uid',
    password varchar(60) null comment '管理员的密码',
    constraint admin_username_uindex
        unique (username)
)
    comment '管理员的账号';

alter table admin
    add primary key (username);

INSERT INTO blog.admin (username, password) VALUES ('admin', '$2a$10$SFaxAU3j7ZoGhSTwK3r0ke0bcXSoGOgHeqyJ9/tyfX0k4C70guM2i');