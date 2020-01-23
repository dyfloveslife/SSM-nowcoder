# nowcoder 讨论平台

# 相关技术与开发环境
- Spring Boot
- Spring、Spring MVC、MyBatis
- Redis、MySQL、Kafka、Elasticsearch
- Spring Security、Spring Actuator
- Maven、Tomcat、Git

# 环境搭建与说明
- pom.xml
  - org.springframework.boot: 2.1.5.RELEASE
  - JDK version: 1.8
  - mysql-connector-java: 8.0.16
  - org.mybatis.spring.boot: 2.0.1

数据库使用 MySQL-8.0.15，数据库可视化软件 MySQL-workbench，数据源使用 HikariDataSource，并设置最大连接、最小连接以及超时时间等。

配置 MyBatis 属性，使之自动生成主键，并开启数据库字段名与 Bean 实体名相匹配。日志等级设置为 debug，以便可以查看具体执行的 sql 语句。

使用 logback 记录日志，Spring Email 发送邮件，便于后序的注册操作。

# 注册页面
使用模板复用顶部区域内的链接。通过点击顶部区域的链接，打开注册页面，然后进行相应的注册。

在注册的时候，需要通过表单提交数据，然后服务器检查账号是否已经存在，邮箱是否已经注册，没有的话执行注册，最后服务器发送激活邮件。

客户端在收到激活邮件后，点击链接即可激活邮箱。

# 会话管理
使用分布式部署的时候，为什么使用 session 会存在问题？

客户端在访问服务器的时候，会在不同的服务器上产生多个 session，这样会造成服务器之间得不到之前的 session。一种解决方式是可以设置 nginx 负载均衡的分配策略；另一种方式是黏性 session，对于同一个 ip，每次只能访问其中一个服务器，但这样的话不会产生负载均衡的效果；还有一种方式是同步session，即当某个服务器在产生 session 的时候，会在其它服务器上产生同样的 session，这样的话每个服务器都存放了同样的 session 数据，但这种方式会对服务器的性能产生影响，同时服务器之间会产生耦合，不会那么的独立。还有一种共享 session，即将所有的 session 数据存放在一台服务器中，然后其它的服务器在每次需要获取 session 的时候，直接从这台服务器获取即可，但是也同样存在问题，这台服务器要是挂了，那么所有的 session 数据也就没有了。现在主流的方式是让这些服务器共同访问数据库集群，并将 session 保存在数据库中。但是，由于一些关系型数据库大多是把数据存放在硬盘里，相对于内存，其访问速度稍微有些慢，所以可以将 session 存放在非关系型数据库中，如 Redis。