# PDS生鲜采购配送系统

#### 介绍
系统后台包含三个子系统模块：
##### 1. 采购配送系统
包含客户微信小程序下单、后台代客下单、订单汇总、分拣、配送、各类订单打印
##### 2. 零售门店收银系统
支持零售门店商品管理、供应商管理、订单管理、收银、智能终端相连
##### 3. 微信开放平台管理
微信开放平台管理、支持微信小程序授权，模板管理，微信审核，发布上线管理等一站式操作
##### 3. 其他
进销存、仓储管理、账单管理(应收、应付)

账户支持LDAP配置,与内部系统账号打通。


#### 系统说明

本系统大部分代码应用于生产环境,久经考验。各个子系统模块可供二次开发应用其他业务场景，核心主要围绕 商品、订单、用户、账单等模块。<br/>
开源版本仅作为学习，参考。<br/>
包含的功能列表如下：<br/>
![输入图片说明](https://images.gitee.com/uploads/images/2020/0806/165520_5052581a_7837037.png "屏幕截图.png")

详情加客服微信了解：<br/>
![输入图片说明](https://images.gitee.com/uploads/images/2020/0805/143525_be83aa45_7837037.png "Wechat-logo.png")


商业版本支持更多高阶功能，及多种应用场景。<br/>
采用最新 <http://wwww.pig4cloud.com>微服务架构.<br/>
更多高阶功能请移步 <http://www.haohanscm.com/demo> <br/>

商业版PDS采购配送系统 <br/>

产品功能：<http://haohanscm.com/demo/72-cn.html> <br/>
视频介绍：<https://www.bilibili.com/video/BV1vB4y1P7k7> <br/>

体验账号登录地址：<http://cloud.haohanscm.com> <br/>
企业code: cs01 
账号: cscp
秘钥: cs123456


#### 技术说明
后台管理系统基于Jeesite二开完成，maven依赖，额外第三方类库JAR包<br/>
1、后端<br/>
核心框架：Spring Framework 4.1 <br/>
安全框架：Apache Shiro 1.2 <br/>
视图框架：Spring MVC 4.1 <br/>
服务端验证：Hibernate Validator 5.2 <br/>
布局框架：SiteMesh 2.4 <br/>
工作流引擎：Activiti 5.21 <br/>
任务调度：Spring Task 4.1 <br/>
持久层框架：MyBatis 3.2 <br/>
数据库连接池：Alibaba Druid 1.0 <br/>
缓存框架：Ehcache 2.6、Redis <br/>
日志管理：SLF4J 1.7、Log4j <br/>
工具类：Apache Commons、Jackson 2.2、Xstream 1.4、Dozer 5.3、POI 3.9 <br/>
消息中间件：Apache-Rocketmq <br/>

2. 前端 代码VUE+Element-UI 参考 <https://github.com/haohanscm/pds-ui>



#### 安装教程
1.  安装redis、mysql,rocketmq
2.  导入数据脚本 cloud.sql 
3.  修改配置文件 haohan.properties 设置 数据库连接，redis，rocketmq
4.  数据库连

登录系统：账号密码 haohan/haohan

#### 使用说明
1. 已开源的代码，授权协议采用 AGPL v3 + Apache Licence v2 进行发行。
2. 您可以免费使用、修改和衍生代码，但不允许修改后和衍生的代码做为闭源软件发布。
3. 修改后和衍生的代码必须也按照AGPL协议进行流通，对修改后和衍生的代码必须向社会公开。
4. 如果您修改了代码，需要在被修改的文件中进行说明，并遵守代码格式规范，帮助他人更好的理解您的用意。
5. 在延伸的代码中（修改和有源代码衍生的代码中）需要带有原来代码中的协议、版权声明和其他原作者规定。
6. 您可以应用于商业软件，但必须遵循以上条款原则（请协助改进本作品）。


#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

开源不易，SQL 在资源包里。

商业版本采用最新<http://wwww.pig4cloud.com>微服务架构，更多高阶功能请移步 <http://www.haohanscm.com/demo>
