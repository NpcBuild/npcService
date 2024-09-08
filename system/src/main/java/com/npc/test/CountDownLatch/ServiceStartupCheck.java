package com.npc.test.CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author NPC
 * @description 服务器董检查（在分布式系统中，系统启动时需要依赖多个服务，确保所有服务都启动完成后，主线程再执行，保证系统的稳定性）
 * @create 2024/8/16 12:39
 */
public class ServiceStartupCheck {
    public static void main(String[] args) throws InterruptedException {
        int numberOfServices = 3;
        CountDownLatch latch = new CountDownLatch(numberOfServices);

        for (int i =0; i < numberOfServices; i++) {
            new Thread(new Service(latch, "Service-" + (i + 1))).start();
        }

        latch.await(); // 等待所有服务启动
        System.out.println("所有服务已启动，系统启动完成。");
    }

}
