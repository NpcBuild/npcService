package com.npc;

import com.npc.client.Netty.server.NettyServer;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = {"com.npc"},annotationClass = Mapper.class)
@EnableScheduling   //开启定时任务
public class StartApplication extends SpringBootServletInitializer {
    private final static Logger logger = LoggerFactory.getLogger(StartApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StartApplication.class);
    }

    public static void main(String[] args) {
//        普通模式
        ConfigurableApplicationContext ctx = SpringApplication.run(StartApplication.class, args);
//        自定义模式
//        new SpringApplicationBuilder(StartApplication.class)
//                .bannerMode(Banner.Mode.OFF)  //取消显示启动文字图
//                .run(args);
        logger.info(StartApplication.class.getSimpleName() + " is success!");
        try {
            System.out.println("https://blog.csdn.net/moshowgame");
            System.out.println("http://127.0.0.1:6688/netty-websocket/index");
            new NettyServer(12345).start();
        } catch (Exception e) {
            System.out.println("NettyServerError:" + e.getMessage());
        }
    }

}
