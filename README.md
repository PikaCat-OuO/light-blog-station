# 轻博客小站

#### 介绍

大学JAVA课程项目，轻博客小站\
本项目非完全前后端分离，一个页面一个Controller\
如果需要采用完全前后端分离则前端用Vue，一个表一个RestController

#### 软件架构

前端:html、css、js、thymeleaf、layui 后端:springboot、springsecurity、mybatis-plus

#### 安装教程

下载回去之后直接用idea打开即可运行，可以用maven打包成jar文件独立发布\
需要先运行sql文件夹内的sql保证数据库内有数据，然后需要修改application-xxx.yml中的数据库链接信息为自己的数据库。

#### 使用说明

使用Docker:
复制发行版文件(轻博客小站v1.1.jar)、Dockerfile、docker-compose.yml到同一目录下

```shell
docker-compose up
```


不使用Docker:
```shell
java -jar 轻博客小站v1.1.jar
```

访问示例(较慢，1核1G，1Mb/s):http://116.63.142.107/

#### 参与贡献

PikaCat


#### 特别说明
近期网站遭到一些恶意攻击，请这些"黑客"们自重。\
首先，本人就是搞CTF的，你想黑我服务器难上加难。但是从你们黑不进来的情况来看，网站的安全性做得非常好。\
其次，你们的痕迹我这里证据齐全，IP也被我记录了。\
\
部分证据如下：\
恶意HTTP报文
![恶意HTTP报文](https://images.gitee.com/uploads/images/2021/0625/154913_1433a1f0_7628839.png "屏幕截图.png")
尝试缓冲区溢出
![尝试缓冲区溢出](https://images.gitee.com/uploads/images/2021/0625/154936_fbcee626_7628839.png "屏幕截图.png")
请求参数包含恶意字符
![请求参数包含恶意字符](https://images.gitee.com/uploads/images/2021/0625/155103_6ea39a28_7628839.png "屏幕截图.png")
PHP漏洞尝试(大哥，我这是JAVA的后台，😓)
![PHP漏洞尝试(大哥，我这是JAVA的后台，😓)](https://images.gitee.com/uploads/images/2021/0625/155011_6f80a55c_7628839.png "屏幕截图.png")
PHP漏洞再次尝试
![PHP漏洞再次尝试](https://images.gitee.com/uploads/images/2021/0625/155134_6d97f5af_7628839.png "屏幕截图.png")
尝试;sh多行命令注入执行恶意指令。
![命令注入](https://images.gitee.com/uploads/images/2021/0627/110245_24492428_7628839.png "屏幕截图.png")
半夜三更ssh爆破
![半夜三更ssh爆破](https://images.gitee.com/uploads/images/2021/0625/155248_17468e90_7628839.png "屏幕截图.png")
半夜三更ssh爆破2
![半夜三更ssh爆破2](https://images.gitee.com/uploads/images/2021/0625/155311_1bf88327_7628839.png "屏幕截图.png")