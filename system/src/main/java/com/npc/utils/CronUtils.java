package com.npc.utils;

import org.springframework.scheduling.support.CronExpression;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author NPC
 * @description cron表达式工具类
 * @create 2025/2/21 16:58
 */
public class CronUtils {
    public static String getNext(String cronExpression) {
        try {
            CronExpression cron = CronExpression.parse(cronExpression);
            LocalDateTime nextExecutionTime = cron.next(LocalDateTime.now());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = nextExecutionTime.format(formatter);
            System.out.println("最近一次符合的时间: " + formattedTime);
            return formattedTime;
        } catch (IllegalArgumentException e) {
            System.err.println("无效的 cron 表达式: " + cronExpression);
            return null;
        }
    }
}
