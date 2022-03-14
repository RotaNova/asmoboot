######                                                     <img src="https://github-resources.oss-cn-hangzhou.aliyuncs.com/logo.png" alt="logo" style="zoom:50%;" />							

#					 ASMO BOOT v1.0


​						 基于SpringBoot开发的快速开发框架[[AsmoBoot官网](https://www.asmoboot.com)]

​							                     [中文文档](https://rotanova.yuque.com/books/share/1f659d3d-1316-4c68-93bf-3498f1b89e8a/dpbcfg) | 英文文档

[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/RotaNova/rotanova-boot/blob/master/LICENSE)

[![C++](https://img.shields.io/badge/language-java-red.svg)](https://www.java.com/zh-CN/)

# 平台简介

​	在开发了一些实际工程项目后，发现一些常见的权限、对接、消息通知等等问题。由于每次定制化项目都要进行一些开发，不利于项目长期维护。参考若依、JEECGBOOT以及一些低代码开发框架的启发，我们设计了一套自己的快速开发框架。具备行级权限管理，安全登录防护以及轻量级的数据中台功能。除此之外，还支持集成了Amis等低代码开发框架，更多内容请自行探索。

​	此外，在项目实施中，我们还额外基于zlmediakit开发了视频监控平台，支持GB28181、RTSP等协议，并具备完整的级联平台以及PTZ控制；由于项目以物联网性质为主，所以还参考阿里云物联网平台开发了基于物模型管理的设备管理模块；此外，还有诸如工地实名制平台对接、智慧停车场、智慧用电等等平台模块，提供有偿服务。

适用项目
-----------------------------------

​		可以应用在任何J2EE项目的开发中，适用SAAS项目、企业信息管理系统（MIS）、内部办公系统（OA）、企业资源计划系统（ERP）、客户关系管理系统（CRM）等，现成的管理框架体系，可以显著提高开发效率，降低开发成本。

## 配套项目

Web前端（Vue)：https://github.com/RotaNova/asmoboot-web

Uni-app:  https://github.com/RotaNova/asmoboot-app

交流群QQ: [189577357](https://github-resources.oss-cn-hangzhou.aliyuncs.com/asmobootQQ.JPG)

项目支持联系（游先生）：

QQ: 365278161 

Email: qilinyou@rotanovaiot.com



# 功能模块

## 平台配置

![platform](https://github-resources.oss-cn-hangzhou.aliyuncs.com/platform.png)

功能清单

| -数据驾驶舱    |
| :------------- |
| -账户管理      |
| --角色管理     |
| --用户管理     |
| --部门管理     |
| --我的部门     |
| -账号设置      |
| -通告管理      |
| --通知消息     |
| --系统消息     |
| --告警消息     |
| -网络配置      |
| -系统监控      |
| -系统维护      |
| --时间配置     |
| --升级维护     |
| --日志管理     |
| -安全管理      |
| -高级配置      |
| --钉钉对接配置 |
| --LDAP         |
| -平台配置      |
| --样式配置     |
| --消息通知配置 |
| --消息中心     |
| --我的消息     |
| --消息接收配置 |
| -资源管理      |
| --页面管理     |
| --接口管理     |
| --数据字典     |
| --表格管理     |
| --数据服务     |
| ---数据源管理  |
| ---API管理     |
| ---应用注册    |
| -问题反馈      |
| -反馈处理      |



## 设备配置

![devicesManage](https://github-resources.oss-cn-hangzhou.aliyuncs.com/devicesmanage.png)

## 基础资料

![basicInfo](https://github-resources.oss-cn-hangzhou.aliyuncs.com/basicInfo.png)

## 工地实名制

![construction](https://github-resources.oss-cn-hangzhou.aliyuncs.com/construction.png)

## 智慧安防

![isc](https://github-resources.oss-cn-hangzhou.aliyuncs.com/isc.png)

## 智能停车系统

![carManage](https://github-resources.oss-cn-hangzhou.aliyuncs.com/carmanage.png)

## 考勤管理系统

![checkIn](https://github-resources.oss-cn-hangzhou.aliyuncs.com/checkin.png)



# 开放能力

## AI能力支持

现有算法清单

![](https://github-resources.oss-cn-hangzhou.aliyuncs.com/aiList.png)

![AIdemo](https://github-resources.oss-cn-hangzhou.aliyuncs.com/AI.jpeg)

在我们平台中，可自由针对接入的摄像头、无人机监控等视频流数据配置AI扩展能力，快速交付业务。





为什么选择该项目进行二次开发?
===================================

* 1.采用最新主流前后分离框架（Springboot+Mybatis+antd），容易上手; 代码生成器依赖性低,灵活的扩展能力，可快速实现二次开发;

* 2.支持微服务SpringCloud Alibaba，提供切换机制支持单体和微服务自由切换

* 3.封装完善的用户、角色、菜单、组织机构、数据字典、在线定时任务等基础功能，支持访问授权、按钮权限、数据权限等功能

* 4.常用工具类封装(定时任务,短信接口,邮件发送,Excel导入导出等),基本满足大部分项目需求

* 5.高级搜索过滤器：查询功能语句自动生成，后台动态拼SQL追加查询条件；支持多种匹配方式（全匹配/模糊查询/包含查询/不匹配查询）；

* 6.数据权限（精细化数据权限控制，控制到接口级别，实现同一个人同一页面增删改查不同权限)

* 7.支持SAAS服务模式，提供SaaS多租户架构方案。

* 8.分布式文件服务，集成minio、阿里OSS等优秀的第三方，提供便捷的文件上传与管理，同时也支持本地存储。

* 9.提供单点登录CAS集成方案

* 10.低代码能力：支持amis代码

* 11.低代码能力: 支持平台在线编辑sql语句，且支持mybatis语法，可实现大部分增删改查等简易接口场景。并支持连接操作主流数据库类型

* 12.提供系统监控，实时跟踪系统运行情况（监控 Redis、Tomcat、jvm、服务器信息、请求追踪、SQL监控）

* 13.消息中心（支持短信、邮件、微信推送等等）

* 14.集成Websocket消息通知机制

* 15.采用maven分模块开发方式

* 16.支持菜单多种路由跳转打开形式

* 17.权限控制采用 RBAC（Role-Based Access Control，基于角色的访问控制）

  


技术架构
===================================
#### 开发环境

- 语言：Java 8

- IDE(JAVA)： IDEA / Eclipse安装lombok插件 

- IDE(前端)： WebStorm 或者 IDEA

- 依赖管理：Maven

- 数据库：MySQL5.7+ 

- 缓存：Redis


#### 后端
- 基础框架：Spring Boot 2.2.5.RELEASE
- 持久层框架：Mybatis-plus 3.4.1
- 安全框架：Apache Shiro 1.7.0，Jwt 3.11.0
- 数据库连接池：阿里巴巴Druid 1.1.22
- 缓存框架：redis
- 日志打印：logback
- 消息队列：rabbitMq
- 定时执行框架: xxl-job
- 其他：fastjson,Swagger-ui ,easyExcel,lombok,hutool等。



项目部署说明
====

项目搭建环境要求
----

- 1.推荐安装docker-ce 17版本以上

- 2.推荐安装docker-compose 1.25.0版本

- 3.推荐使用centos7为运行环境


项目端口访问要求
----

```
*1.8899端口为API接口
*2.80端口为页面访问端口
*3.9000及9001为分布式存储minio端口 
*4.10504为websocket通信端口 
5.3308为Mysql数据库端口

以上加*号务必添加防火墙白名单，否则将影响正常使用
```

一键部署
----

​	① 下载源码，进入boot_start目录；

​	② 修改.env文件下的环境参数

​	*MINIO_ENDPOINT 为部署服务器可访问IP+9000端口 请勿使用localhost以及127.0.0.1 例如 http://hostIP:9000

```bash
MINIO_ACCESS_KEY 为minio access_key

MINIO_SECRET_KEY 为minio secret_key

DATASOURCE_PWD 为mysql密码 ，账号默认为root

RABBITMQ_DEFAULT_USER 为rabbitmq账号

RABBITMQ_DEFAULT_PASS 为rabbitmq密码	
```

加*的请务必根据部署环境进行调整更改，否则会无法启动

​	③ 将boot_start该目录下所有文件及文件夹复制到服务器上干净目录，一键部署命令。

```bash
docker-compose up -d
```

系统初始化效果
----

![image-20220114162347183](http://rn-test-face-static-test-resource.oss-cn-hangzhou.aliyuncs.com/9CA0D08D-B07B-4ae4-A58A-0A7C7B627C92.png)



Maven包缺失问题
----

该项目存在部分私有仓库包，请将工程maven目录下的package.zip文件解压至maven本地仓库后重新导入





附属文档
====

- [平台使用手册](https://rotanova.yuque.com/books/share/1f659d3d-1316-4c68-93bf-3498f1b89e8a)



备注
----

> 1.一键演示部署成功后，默认初始账号及密码为admin
>
> 2.如需重新部署mysql，请先备份脚本，直接重启可能会导致数据丢失

