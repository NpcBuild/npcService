# 定时任务Scheduled

1. Scheduled
2. Quartz
   定时任务的诸多要素：任务名称、数量、状态、运行频率、运行时间等
   Quartz的持久化
   JobStore就是用来存储任务和触发器相关的信息的
   Quartz包含两种存储任务的方式：   1、内存（RAMJobStore）    --默认
                                2、数据库（JDBCJobStore）
# 线程池（threadPool）
1. ThreadPoolTaskExecutor