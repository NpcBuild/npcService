# Netty客户端
## 简介
Netty 是一个利用 Java 的高级网络的能力，隐藏其背后的复杂性而提供一个易于使用的 API 的客户端/服务器框架。

对NIO进行了封装
```
启动器中需要new一个NettyServer，并显式调用启动netty。

@SpringBootApplication
public class SpringCloudStudyDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStudyDemoApplication.class,args);
        try {
        new NettyServer(12345).start();
            System.out.println("https://blog.csdn.net/moshowgame");
            System.out.println("http://127.0.0.1:6688/netty-websocket/index");
        }catch(Exception e) {
            System.out.println("NettyServerError:"+e.getMessage());
        }
    }
}
```
channelRead和channelRead0的区别
```
```