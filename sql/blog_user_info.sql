create table user_info
(
    username    varchar(20)  not null comment '用户名',
    password    varchar(60)  not null comment '密码',
    realname    varchar(20)  null comment '姓名',
    gender      varchar(1)   not null comment '性别',
    age         smallint     null comment '年龄',
    birthday    date         null comment '出生年月日',
    telephone   varchar(11)  null comment '电话号码',
    address     varchar(40)  null comment '家庭住址',
    description varchar(200) null comment '个人简介',
    constraint user_info_username_uindex
        unique (username)
)
    comment '用户信息表';

alter table user_info
    add primary key (username);

INSERT INTO blog.user_info (username, password, realname, gender, age, birthday, telephone, address, description) VALUES ('user1', '$2a$10$1JdgGYkom7vAZrcXrH9/G..ysXCou4VfNRymk9qJP0VrQ83zgTW7O', '张二狗', '男', 24, '1997-04-09', '18828838848', '体育西路', 'PlayPlayPlay!YES!');
INSERT INTO blog.user_info (username, password, realname, gender, age, birthday, telephone, address, description) VALUES ('user2', '$2a$10$1JdgGYkom7vAZrcXrH9/G..ysXCou4VfNRymk9qJP0VrQ83zgTW7O', null, '男', 24, '1996-04-11', null, null, '先学C++基础语法，再学习STL模板库，再学习WindowsAPI开发，最后再学习Qt技术');