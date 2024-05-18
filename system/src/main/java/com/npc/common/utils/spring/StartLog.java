package com.npc.common.utils.spring;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NPC
 * @description
 * bean的初始化过程中，执行的先后顺序：Constructor > @Autowired > @PostConstruct 即首先执行构造方法，然后进行依赖注入，最后执行初始化操作
 * @create 2024/5/18 9:55
 */
@Slf4j
public class StartLog {
    private static int count = 0;
    public static void log(String content) {
        log.error("启动过程" + ++count + ":" + content);
    }
}
