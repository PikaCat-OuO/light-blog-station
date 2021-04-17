create table article_info
(
    article_uid bigint auto_increment comment '文章UID',
    title       varchar(30)                         not null comment '文章的标题',
    content     longtext                            not null comment '文章的内容',
    category    varchar(10)                         not null comment '文章分类',
    keywords    varchar(20)                         null comment '索引关键字',
    open        tinyint(1)                          not null comment '文章是否公开',
    commentable tinyint(1)                          not null comment '能否评论',
    create_time timestamp default CURRENT_TIMESTAMP not null comment '文章的创建时间',
    modify_time timestamp default CURRENT_TIMESTAMP not null comment '文章的修改时间',
    username    varchar(20)                         not null comment '所属用户名',
    constraint article_info_article_uid_uindex
        unique (article_uid),
    constraint article_info_category_name_fk
        foreign key (category) references category (name)
            on update cascade on delete cascade,
    constraint article_info_user_info_username_fk
        foreign key (username) references user_info (username)
            on update cascade on delete cascade
)
    comment '文章信息表';

alter table article_info
    add primary key (article_uid);

INSERT INTO blog.article_info (article_uid, title, content, category, keywords, open, commentable, create_time, modify_time, username) VALUES (1, '一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十', '<p style="text-align: center;"><span style="text-decoration: underline; font-size: 18pt;"><em><strong>只要不一键三连就可以了，听说下载回来看不会增加播放量!</strong></em></span></p>
<p style="text-align: center;"><span style="text-decoration: underline; font-size: 18pt;"><em><strong>asdasdasdsada</strong></em></span></p>', '技术类', '一二三四五六七八九十一二三四五六七八九十', 1, 0, '2021-04-01 17:00:45', '2021-04-10 21:56:28', 'user1');
INSERT INTO blog.article_info (article_uid, title, content, category, keywords, open, commentable, create_time, modify_time, username) VALUES (2, '现在生活的人，为什么喜欢熬夜', '因为手机太好玩了，看到半夜三更不想碎觉!', '生活类', '睡觉,熬夜', 1, 0, '2021-04-02 03:00:59', '2021-04-07 03:01:03', 'user2');
INSERT INTO blog.article_info (article_uid, title, content, category, keywords, open, commentable, create_time, modify_time, username) VALUES (3, 'Java如何学好', '先学JAVASE，再学网页三件套，再学JAVAWEB，servlet，最后再学springboot，mybatis等', '技术类', 'JAVA', 1, 0, '2021-04-04 03:01:06', '2021-04-07 03:01:16', 'user1');
INSERT INTO blog.article_info (article_uid, title, content, category, keywords, open, commentable, create_time, modify_time, username) VALUES (4, 'C++如何学习', '先学C++基础语法，再学习STL模板库，再学习WindowsAPI开发，最后再学习Qt技术', '技术类', 'C++,STL,Qt', 1, 0, '2021-01-09 03:01:10', '2021-04-09 03:01:21', 'user2');
INSERT INTO blog.article_info (article_uid, title, content, category, keywords, open, commentable, create_time, modify_time, username) VALUES (5, 'Java如何学才能学好', '先学JAVASE，再学网页三件套，再学JAVAWEB，servlet，最后再学springboot，mybatis等', '技术类', 'java', 1, 0, '2021-04-06 11:01:06', '2021-04-10 16:32:38', 'user1');
INSERT INTO blog.article_info (article_uid, title, content, category, keywords, open, commentable, create_time, modify_time, username) VALUES (6, '美文', '青春岁月，我们行走在岁月中，不断地遗落。

　　一起走过的路，落下的泪，唱过的歌……随着时间的沉淀，已经积成厚厚的青苔，不可退却。

　　忆起读书时代，才过去一年，却已觉得遥不可及。课本、教室、宿舍、食堂，一切都已经成为陌生的词语。偶尔翻阅那些课本，却找不到当初的心情，剩下的只是落寞。

　　寂寞的盛夏，思绪走了好远。彼时欢笑满天的我们，此时沉静不语；彼时的梦想，此时早已丢弃在某个角落……时间的改变，让那些青春的悸动都言不由衷，只能任由其一点点成为阴霾。

　　在些许时间，为逝去的年华哭泣，为死掉的时光悼念，但终觉得徒劳，一切似乎没有意义。面对于太多现实，我只能选择落荒而逃。

　　习惯于当倾听者，听那些所信任我的人对我的落落之语，而我，却选择沉默。在深夜里，把自己的悲伤诉说给墙壁聆听，在心里不安的时候，选择一个人安静，其实不是把别人隔绝在我的世界之外，而是不想在难过的时候，把自己过于负面的情绪带给他人，所以沉默是最好的选择。

　　青春是道明媚的忧伤，左脸灿烂右脸阴郁。年轻的心背负着太多的梦想，来不及实现就已早早收场。青春就在明媚参半里一晃而过，来不及品味来不及悼念。

　　去年的这个时候我结束了我四年的爱情，而这一年以来我在不断的回味不断回味……

　　又是盛夏……寂寞的盛夏……', '技术类', 'JAVA', 1, 0, '2021-04-04 03:01:06', '2021-04-07 03:01:16', 'user1');
INSERT INTO blog.article_info (article_uid, title, content, category, keywords, open, commentable, create_time, modify_time, username) VALUES (9, '如何白嫖B站的视频', '听说下载回来就不会增加播放量了，记得要断网播放哦！', '生活类', 'B站，白嫖', 1, 1, '2021-04-10 16:54:03', '2021-04-10 16:54:03', 'user1');
INSERT INTO blog.article_info (article_uid, title, content, category, keywords, open, commentable, create_time, modify_time, username) VALUES (12, 'XX帅不帅?有没有XX小哥哥的微信账号？', '<p style="text-align: center;">今天在公开课上，他坐我前面，转过来问我问题，特别的温柔，😀<strong><span style="color: #e67e23;">我爱上他了</span></strong>，能不能求个<span style="text-decoration: underline;"><em><span style="color: #e03e2d; text-decoration: underline;">微信号</span></em></span>，当时害羞不敢跟他要</p>', '生活类', 'XX，微信', 1, 1, '2021-04-14 14:49:35', '2021-04-15 20:17:35', 'user1');
INSERT INTO blog.article_info (article_uid, title, content, category, keywords, open, commentable, create_time, modify_time, username) VALUES (13, '太好了', '<p>123123</p>', '经济类', '1111', 1, 1, '2021-04-16 16:55:18', '2021-04-16 16:55:18', 'user1');