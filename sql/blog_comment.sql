create table comment
(
    comment_uid bigint auto_increment comment '评论的UID',
    article_uid bigint                             not null comment '文章的UID',
    comment     varchar(1000)                      not null comment '文章的评论',
    username    varchar(20)                        not null comment '评论的用户名',
    time        datetime default CURRENT_TIMESTAMP null comment '评论的时间',
    constraint comment_comment_uid_uindex
        unique (comment_uid),
    constraint comment_article_info_article_uid_fk
        foreign key (article_uid) references article_info (article_uid)
            on update cascade on delete cascade,
    constraint comment_user_info_username_fk
        foreign key (username) references user_info (username)
            on update cascade on delete cascade
)
    comment '文章的评论';

alter table comment
    add primary key (comment_uid);

INSERT INTO blog.comment (comment_uid, article_uid, comment, username, time) VALUES (5, 9, '什么啊，人家up那么辛苦，你就眼睁睁的白嫖up的视频', 'user1', '2021-04-11 20:59:55');
INSERT INTO blog.comment (comment_uid, article_uid, comment, username, time) VALUES (13, 9, '12312312312', 'user1', '2021-04-11 21:43:09');
INSERT INTO blog.comment (comment_uid, article_uid, comment, username, time) VALUES (16, 9, '12312312312', 'user1', '2021-04-11 21:57:31');
INSERT INTO blog.comment (comment_uid, article_uid, comment, username, time) VALUES (21, 9, '就是啊', 'user1', '2021-04-12 20:13:41');
INSERT INTO blog.comment (comment_uid, article_uid, comment, username, time) VALUES (22, 9, '非常棒', 'user1', '2021-04-12 20:45:49');
INSERT INTO blog.comment (comment_uid, article_uid, comment, username, time) VALUES (23, 12, 'XX的微信我有，想要吗?', 'user1', '2021-04-12 20:49:50');
INSERT INTO blog.comment (comment_uid, article_uid, comment, username, time) VALUES (25, 9, '太草了', 'user1', '2021-04-15 21:02:42');