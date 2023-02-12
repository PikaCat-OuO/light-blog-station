create table super_admin
(
    username varchar(20) not null comment '超级管理员的uid',
    password varchar(60) not null comment '超级管理员的密码',
    constraint super_admin_username_uindex
        unique (username)
)
    comment '超级管理员表格';

alter table super_admin
    add primary key (username);

INSERT INTO blog.super_admin (username, password) VALUES ('superadmin', '$2a$10$1JdgGYkom7vAZrcXrH9/G..ysXCou4VfNRymk9qJP0VrQ83zgTW7O');