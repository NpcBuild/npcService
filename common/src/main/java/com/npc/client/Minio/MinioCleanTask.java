package com.npc.client.Minio;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * @program: npcService
 * @description
 * @author: feiyang
 * @create: 2025/12/04 16:53
 **/
@Component//注册Bean
@RequiredArgsConstructor
@Slf4j
public class MinioCleanTask {

    private final MinioUtil minioUtil;

    /**
     * 定时清理 MinIO 中早于当前日期的日期目录（格式：yyyy-MM-dd/）
     * 执行时间：每月1号凌晨3点
     */
    @Async(value = "threadPool") // 异步注解
    @Scheduled(cron = "0 0 3 1 * ?")
    public void minioClean() {
        try {
            log.info("MinIO 清理任务开始执行...");
            // 明确语义：删除早于今天的所有日期目录（不含今天）
            LocalDate today = LocalDate.now();
            log.info("当前日期：{}, 开始清理早于该日期的目录", today);
            int deleteCount = minioUtil.deleteDateFoldersBefore(today);
            log.info("MinIO 清理任务执行完成，共删除 {} 张图片", deleteCount);
        } catch (Exception e) {
            // 防止定时任务因异常停止
            log.error("MinIO 清理任务执行失败", e);
        }
    }
}
