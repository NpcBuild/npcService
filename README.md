@[toc]
# 对外接口
# 健康监控
http://localhost:1314/monitor/beans
http://localhost:1314/monitor/health
http://localhost:1314/monitor/shutdown

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
