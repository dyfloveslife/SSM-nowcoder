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