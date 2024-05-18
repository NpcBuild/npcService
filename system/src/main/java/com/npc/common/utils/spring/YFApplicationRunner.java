package com.npc.common.utils.spring;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author NPC
 * @description 项目启动时运行，可以在应用启动后访问命令行参数（ApplicationArguments）
 * @create 2024/5/18 10:09
 */
@Component
public class YFApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 是否存在name的标志位
        boolean containsOption = args.containsOption("name");
        if (containsOption) {
            // 获取命令行name参数的值
            String name = args.getOptionValues("name").get(0);
        }
        StartLog.log("YFApplicationRunner执行初始化操作；参数：" + args + "......");
    }
}
