package com.npc.utils;

import cn.hutool.core.date.DateUtil;
import org.springframework.scheduling.support.CronExpression;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author NPC
 * @description cron表达式工具类
 * @create 2025/2/21 16:58
 */
public class CronUtils {
    public static String getNext(String cronExpression, LocalDateTime now) {
        try {
            CronExpression cron = CronExpression.parse(cronExpression);
            if (now == null) {
                now = LocalDateTime.now();
            }
            LocalDateTime nextExecutionTime = cron.next(now);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = nextExecutionTime.format(formatter);
            System.out.println("最近一次符合的时间: " + formattedTime);
            return formattedTime;
        } catch (IllegalArgumentException e) {
            System.err.println("无效的 cron 表达式: " + cronExpression);
            return null;
        }
    }

    /**
     * 计算cron表达式剩余执行次数
     * @param cronExpression cron表达式
     * @return 剩余执行次数，如果为null表示无限次
     */
    public static Long getRemainingExecutions(String cronExpression, LocalDateTime endDate) {
        try {
            Date now = new Date();
            long count = 0;
            Date next = now;

            // 限制最大计算次数，防止无限循环
            long maxIterations = 10000;
            long iterations = 0;

            Date end = DateUtils.toDate(endDate);
            while (next != null && iterations < maxIterations && next.before(end)) {
                count++;
                String nextStr = getNext(cronExpression, DateUtil.toLocalDateTime(next));
                System.out.println("nextStr: " + count);
                next = DateUtils.parseDate(nextStr);
                iterations++;

                // 如果发现循环模式或者超过合理范围，则认为是无限次
                if (count > 1000) {
                    return null; // 表示无限次
                }
            }
            if (next != null && next.after(end)) {
                count--;
            }

            return count;
        } catch (Exception e) {
            e.printStackTrace();
            // 处理异常情况
            return null;
        }
    }
}
