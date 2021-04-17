create table category
(
    name varchar(10) not null comment '分类信息',
    constraint category_name_uindex
        unique (name)
)
    comment '分类表';

alter table category
    add primary key (name);

INSERT INTO blog.category (name) VALUES ('医学类');
INSERT INTO blog.category (name) VALUES ('技术类');
INSERT INTO blog.category (name) VALUES ('生活类');
INSERT INTO blog.category (name) VALUES ('研究类');
INSERT INTO blog.category (name) VALUES ('科技类');
INSERT INTO blog.category (name) VALUES ('经济类');