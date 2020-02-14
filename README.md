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

使用 Kaptcha 生成验证码，配置 MyBatis 属性，使之自动生成主键，并开启数据库字段名与 Bean 实体名相匹配。日志等级设置为 debug，以便可以查看具体执行的 sql 语句。

使用 logback 记录日志，Spring Email 发送邮件，便于后序的注册操作。

一般先根据表中的字段在 entity 中建立相应的实体类，然后在 DAO 层建立相应的 Mapper 接口，然后在 Mapper 中声明一些增删改查的方法，其次可以新建一个 Mapper 文件，在里面填入相应的 sql 语句。当然，也可以直接在 DAO 层的 Mapper 接口的方法上直接写 sql 语句。写完之后，在测试类中测试一下 sql 语句是否正确。在 service 层注入相应的 Mapper，最后在表现层处理相应的请求路径。

在登录页面提交的时候，需要在表单设置提交方式以及提交路径，每个框加上和 controller 一致的 name，这些是发送请求需要进行的设置。

如果有什么问题再回到该页面，对页面进行错误信息的展现，将原来的值使用 value 进行显示，然而是否显示需要根据样式进行动态展示的。

在服务端使用 wkhtmltoimage 将网页模板生成长图。

# 注册页面
使用模板复用顶部区域内的链接。通过点击顶部区域的链接，打开注册页面，然后进行相应的注册。

在注册的时候，需要通过表单提交数据，然后服务器检查账号是否已经存在，邮箱是否已经注册，没有的话执行注册，最后服务器发送激活邮件。

客户端在收到激活邮件后，点击链接即可激活邮箱。

# 会话管理
使用分布式部署的时候，为什么使用 session 会存在问题？

客户端在访问服务器的时候，会在不同的服务器上产生多个 session，这样会造成服务器之间得不到之前的 session。一种解决方式是可以设置 nginx 负载均衡的分配策略；另一种方式是黏性 session，对于同一个 ip，每次只能访问其中一个服务器，但这样的话不会产生负载均衡的效果；还有一种方式是同步session，即当某个服务器在产生 session 的时候，会在其它服务器上产生同样的 session，这样的话每个服务器都存放了同样的 session 数据，但这种方式会对服务器的性能产生影响，同时服务器之间会产生耦合，不会那么的独立。还有一种共享 session，即将所有的 session 数据存放在一台服务器中，然后其它的服务器在每次需要获取 session 的时候，直接从这台服务器获取即可，但是也同样存在问题，这台服务器要是挂了，那么所有的 session 数据也就没有了。

现在主流的方式是让这些服务器共同访问数据库集群，并将 session 保存在数据库中。但是，由于一些关系型数据库大多是把数据存放在硬盘里，相对于内存，其访问速度稍微有些慢，所以可以将 session 存放在非关系型数据库中，如 Redis。

# 拦截器的设置
使用拦截器拦截浏览器的不同请求，根据不同的用户显示不同的网站功能。先定义一个拦截器，然后在配置类中进行配置，实现添加拦截器的方法。在 html 中进行相应的配置。

检查登录状态，避免用户在浏览器输入敏感路径造成的损失。

# 发布帖子
对于发布之前敏感词的过滤问题，采用前缀树实现。使用 AJAX 进行异步请求，从而实现在不刷新整个页面的情况下，更新页面部分数据。

# 事务管理
理解事务的特性，即原子性（Atomicity）、一致性（Consistency）、隔离性（Isolation）、持久性（Durability）。给“添加评论”模块添加事务。

# 处理异常

浏览器发送的请求首先会给表现层，然后再给业务层，最后再给数据层，层层调用。如果数据层出现了异常，则会抛给业务层，然后再抛给表现层。所以，这里对表现层进行统一的异常处理。

对于 Spring Boot 来说，可以将错误页面，如 404.html 放在 templates/error 下。

# 记录日志
假如在业务层需要记录日志，此时为了避免采用硬编码的方式对业务层进行修改，这里使用面向切面编程（AOP）的方式进行统一记录日志。AOP 可以用来实现权限检查、记录日志、事务管理等。

AOP 编程思想：
- target: 目标对象，即已经开发好的多个 bean；
- joinpoint: 切入点，即目标对象上有很多地方可以被织入代码，那些能够被织入代码的地方被称作连接点；
- aspect: 切面，即 AOP 处理代码的方式是将其编写在额外的组件里；
  - pointcut: 切点，即通过切点声明将代码织入到哪些对象的哪些位置；
  - advice: 通知，即声明切面组件处理逻辑的方式；
- waving: AOP 将 aspect 中的代码通过织入（waving）的方式织入到某些连接点（joinpoint）上。

具体的实现方式:
- AspectJ:
  - 编译期织入
- Spring AOP:
  - 运行期织入
  - 只支持方法类型
  - 代理方式
    - JDK 动态代理，在运行时创建接口的代理实例
    - CGLib 动态代理，在运行时创建子类的代理实例

# Spring 整合 Redis
Redis 是基于键值对的 NoSQL 数据库，将数据存放在内存中，其值支持多种数据结构，如 strings、hashes、lists、sets 以及 sorted sets 等，适用于缓存、排行榜、计数器、社交网络、消息队列等。

引具体步骤为：引入依赖、配置 Redis（配置数据库参数、编写配置类，构造 RedisTemplate）以及访问 Redis。

Redis 不完全支持 ACID 特性，实现的方式是：当启用事务，执行一个 Redis 命令的时候，并不会立刻执行该命令，而是将命令放进队列里存储，再执行一个命令的时候，再次放进队列里，直到提交事务的时候，再将队列中的命令一股脑全都执行完。所以，不要在事务之内的命令执行查询操作，这样不会立即获得查询结果。要么提前查，要么等到 Redis 执行完事务后再查。Redis 支持声明式事务和编程式事务，但尽量使用编程式事务。

对点赞功能中的统计点赞数量以及点赞状态使用 Redis 进行存储。对关注、取关功能同样的采用 Redis 实现。

具体的实现方式：
- 首先构建一个专门用于管理 Redis key 的一个工具类，定义好相应的 key，生成返回特定格式 key 的方法；
- 其次在业务层中实现关注或取关的功能，并采用编程式事务，同时生成获取关注者、被关注着、是否已关注等方法；
- 然后在控制层通过异步请求的方式实现相应的功能，返回给页面；
- 最后通过修改页面属性，将需要显示的信息进行修改，即修改页面的消失效果，同时修改部分 JS 代码。

使用 Redis 存储验证码，因为验证码需要频繁的访问与刷新，对性能要求较高，并且不需要永久保存，通常在很短的时间后就会失效。其次，在每次处理请求时，都需要查询用户的登录凭证，这里也使用 Redis 处理。最后，在查询用户信息的时候，访问的频率也很高，所以这里使用 Redis 缓存用户信息。

在设计缓存的时候，先从缓存中取值，能取到的话就用，取不到的话说明值没有初始化到缓存中，此时需要进行初始化。当数据变更时，一般可以更新缓存，或者将缓存删除后再次进行缓存。 也就是说，在查询的时候，不是先去 MySQL 中查，而是先从缓存中取值。

使用 Redis 高级数据结构 HyperLogLog 和 Bitmap 对网站的数据进行统计。


# 使用 Kafka 构建异步消息系统
可以使用阻塞队列（BlockingQueue）解决线程通信的问题，其中的一些阻塞方法，如 put、take 等。同时适用于生产者（产生数据的线程）与消费者（使用数据的线程）模式。具体的实现类有：ArrayBlockQueue、LinkedBlockQueue、PriorityBlockQueue、SynchronousBlockQueue、DelayQueue 等。

对于消息队列的实现方式，大致分为点对点方式（如生产者消费者模式）和发布订阅模式。而对于发布订阅模式，生产者生产商品放到了某个位置，可以有多个消费者同时关注订阅该位置，从而读取消息。 

使用 Kafka 这一分布式流媒体平台可以实现消息系统、日志收集、用户行为追踪、流式处理等，具有高吞吐量、消息持久化、高可靠性、高扩展性等特点。对于消息的持久化，将数据流保存在硬盘中，顺序读取比随机读取的速度要快。

对于一些相关的术语，如 Broker，即 Kafka 的服务器，每一台服务器称之为一个 Broker；Zookeeper，即用来管理集群，它属于单独的一套框架，只不过 Kafka 也需要集群的管理，所以使用了 Zookeeper；Topic，即生产者将消息存放的位置；Partition，即对 Topic 位置进行多个分区，将数据追加到每个分区中；Offset，即消息（数据）在分区（Partition）中存放的索引；可以将分区保存成副本（Replica），当获取数据的时候，可以从主副本（Leader Replica）中获取数据，而从副本（Follower Replica）只负责做备份。

对评论、点赞、关注功能分别定义三种不同的 topic，使其进入到各自的消息队列中，在进入之前，将其封装成相应的事件，开发事件的生产者和消费者，从而实现发送系统通知的功能。

# 使用 Elasticsearch 进行全文搜索
Elasticsearch 是一个分布式、RESTful 风格的搜索引擎，支持对各种类型的数据检索，并且检索速度快，可提供实时的搜索服务。此外，便于水平扩展，每秒可以处理 PB 级海量数据。一些术语：索引（数据库，6.0 以后对应表）、类型（表）、文档（行）、字段（列），集群、节点、分片、副本。集群中的每台服务器称为节点，一个索引在存储的时候可以拆分成多个分片，而副本是对分片的备份。

将帖子保存到 es 中，从 es 中进行搜索与删除帖子。同样的，在进行发布帖子和增加评论时，也是采用异步的方式提交到 es 服务器。

# 使用 Spring Security 进行身份认证和授权
Spring Security 可以对身份进行认证和授权，防止会话固定攻击、点击劫持、csrf 攻击等。

# 任务执行和调度
JDK 线程池、Spring 线程池以及使用分布式定时任务 Spring Quartz。这里涉及到在分布式部署的前提下，为什么使用 Spring Quartz 而不是使用 JDK 线程池和 Spring 线程池。

在分布式系统中，对于多台服务器，假如每台服务器可执行 controller 和 scheduler 操作，此时浏览器通过负载均衡 Nginx 进行选择，如果是一般的页面请求（登录、注册、首页），则会将请求交给某台服务器的 controller。但是每台服务器中的 scheduler 都会做相同的事，例如删除某个日志文件等，这样会显得冗余。而 JDK 线程池和 Spring 线程池不能解决分布式中的问题（它们是基于内存的，而服务器之间内存不共享），但 Spring Quartz 可以，它会把参数存到数据库里，每台服务器中的 Quartz 共同访问同一数据库，从而可以实现共享的操作。

而在项目中，使用定时任务进行热帖排行的功能。对于没有变化的帖子（长时间不点赞、不互动），则不计入统计，而对于经常变化的帖子，可以将其放进缓存里，等到定时任务的时间到了则直接从缓存中取到这些帖子进行计算，这样可以减少计算量。

# 网站的性能优化
这里通过使用 Caffeine 来对网站进行优化，使用 JMeter 进行压力测试，**一般对于变化频率相对较低的数据进行缓存**，之前已经使用过 Redis，所以下面从三个方面对缓存进行设置：

- 本地缓存，将数据存放在应用服务器上，性能最好。例如，Ehcache、Guava、Caffeine 等。
- 分布式缓存，将数据放在 NoSQL 数据库上，通过跨服务器的方式进行缓存。例如，MemCache、Redis 等。
- 多级缓存，通过设置一级缓存（本地缓存）-> 二级缓存（分布式缓存）-> 数据库，可以避免缓存雪崩（缓存失效，大量请求直达数据库），提高系统的可用性。

对于多级缓存，当浏览器访问应用的时候，首先从本地缓存（一级缓存）中查看是否有相应的数据，有则直接返回，没有的话再请求二级缓存（例如 Redis），有数据则返回，没有的话则访问数据库，找到相应数据后需要对数据进行同步，先同步二级缓存，再同步一级缓存，最终才将数据进行返回。同步的目的是便于下次同样访问此资源时，能够更快的从本地缓存（一级缓存）得到响应。

对于缓存的淘汰策略，可以根据最近最久未使用的方式进行淘汰，也可以根据数据的使用率（频繁程度）进行淘汰，也可以根据时间进行淘汰。

优化前：

![before-optimizaiton](https://github.com/dyfloveslife/SSM-nowcoder/blob/master/images/BeforeOptimization.jpg)

优化后：

![after-optimization](https://github.com/dyfloveslife/SSM-nowcoder/blob/master/images/AfterOptimization.jpg)

# 其它
在进行单元测试的时候，测试用例用该保证测试方法的独立性，即待测试的方法不与其它方法产生耦合，如果在测试某一方法时引入了别的业务方法，则在代码改动的时候，对其进行测试产生的数据或者测试结果有可能是不正确的。应遵循**初始化数据**、**执行测试代码**、**验证测试结果**以及**清理测试数据**，这样可以保证每次测试的时候都会产生针对此方法的测试数据，测试完之后再将其删除即可，不会与其它的数据产生歧义。常用的注解有 @BeforeClass、@AfterClass、@Before 以及 @After。

使用 Spring Boot Actuator 对项目进行监控。