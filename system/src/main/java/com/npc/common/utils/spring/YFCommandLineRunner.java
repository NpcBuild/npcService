package com.npc.common.utils.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author NPC
 * @description 项目启动时运行
 * @create 2024/5/18 9:51
 */
@Component
public class YFCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        StartLog.log("YFCommandLineRunner执行初始化操作；参数：" + Arrays.toString(args) + "......");
    }
}
