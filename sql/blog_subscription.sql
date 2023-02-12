create table subscription
(
    subscription_uid bigint auto_increment comment '关注的uid',
    username         varchar(20) not null comment '哪个用户的关注',
    article_uid      bigint      not null comment '关注哪一篇文章',
    constraint subscription_subscription_uid_uindex
        unique (subscription_uid),
    constraint subscription_article_info_article_uid_fk
        foreign key (article_uid) references article_info (article_uid)
            on update cascade on delete cascade,
    constraint subscription_user_info_username_fk
        foreign key (username) references user_info (username)
            on update cascade on delete cascade
)
    comment '用户关注的文章';

alter table subscription
    add primary key (subscription_uid);

INSERT INTO blog.subscription (subscription_uid, username, article_uid) VALUES (6, 'user1', 5);
INSERT INTO blog.subscription (subscription_uid, username, article_uid) VALUES (7, 'user1', 6);