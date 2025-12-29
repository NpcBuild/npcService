# 已移除PackDynamicAutoProxyCreator的@Component ，使其失效。因为启动会报错，此热拔插Aop功能未实现。原文见 https://mp.weixin.qq.com/s/hBVfCkbL0NQCaYb-qVQf0Q

AOP的动态热插拔功能，是一种允许在不重启应用程序的情况下，动态地添加、替换或删除AOP通知（Advice）或通知者（Advisor）
这种技术通过操作Spring AOP框架中的代理对象，主要就是其内部的Advised接口（Spring生成的代理对象会自动实现该接口，不管是基于JDK还是CGLIB），来实现对AOP切面行为的动态控制。