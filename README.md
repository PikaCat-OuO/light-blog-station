# 轻博客小站

#### 介绍

大学JAVA课程项目，轻博客小站\
本项目非完全前后端分离，一个页面一个Controller\
如果需要采用完全前后端分离则前端用Vue，一个表一个RestController

#### 特别说明

因课程需要，WTFPL协议在2021年8月开始才生效，在此之前项目仅供借鉴参考，请勿有任何抄袭行为。

#### 软件架构

前端:html、css、js、thymeleaf、layui 后端:springboot、springsecurity、mybatis-plus

#### 安装教程

下载回去之后直接用idea打开即可运行，可以用maven打包成jar文件独立发布\
需要先运行sql文件夹内的sql保证数据库内有数据，然后需要修改application-xxx.yml中的数据库链接信息为自己的数据库。

#### 使用说明

使用Docker:\
复制发行版文件(轻博客小站v1.1.jar)、Dockerfile、docker-compose.yml到同一目录下\
docker-compose up

不使用Docker:\
java -jar 轻博客小站v1.1.jar

访问示例(较慢，1核1G，1Mb/s):http://116.63.142.107/

#### 参与贡献

PikaCat
