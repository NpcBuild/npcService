@[toc]
# 对外接口
# 健康监控
http://localhost:1314/monitor/beans
http://localhost:1314/monitor/health
http://localhost:1314/monitor/shutdown

# druid sql监控页面
/druid/login.html

# 全局异常处理
UserExceptionHandler
# 项目启动时
ApplicationRunner
CommandLineRunner
(两者唯一的区别是对参数的处理上，ApplicationRunner可以接收更多类型的参数)
加载一些系统参数、完成初始化、预热本地缓存等

# OAuth2
https://mp.weixin.qq.com/s/E6qvcZYWMi4QCoYx_fG4RQ

# JWT
HS512 secret加密

# 缓存
- redis
- guava

# 实施
- 使用Jackson的@JsonFormat注解来指定反序列化时应该使用的格式

# 适配器
HandlerInterceptorAdapter

# 限流
com.npc.core.limit.aop.Limit
@Limit(key = "Limiter:netDisk",permitsPerSecond=1,timeout=500)

# @Async注解
异步任务使用了一个线程池，它的corePoolSize=8, 阻塞队列采用了无界队列LinkedBlockingQueue。一旦采用了这样的组合，最大线程数就会形同虚设，因为超出8个线程的任务，将全部会被放到无界队列里。使得下面的代码变成了摆设。
throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var4);
如果你的访问量非常大，这些任务将全部堆积在LinkedBlockingQueue里。情况好一点的，这些任务的执行会变得延迟很大；情况坏一点的，任务太多将直接造成内存溢出OOM！
org.springframework.aop.interceptor.AsyncExecutionInterceptor
在TaskExecutionAutoConfiguration中，通过生成ThreadPoolTaskExecutor的Bean，来提供默认的Executor。
如果没有SpringBoot的助力，Spring将默认使用SimpleAsyncTaskExecutor。
SimpleAsyncTaskExecutor类设计的非常操蛋，因为它每执行一次，都会创建一个单独的线程，根本没有共用线程池。比如你的TPS是1000，异步执行了任务，那么你每秒将会生成1000个线程！

# 图床（七牛云）
picBed