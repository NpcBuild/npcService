package com.npc.test.CountDownLatch;

import java.util.concurrent.CountDownLatch;

class Service implements Runnable {
        private final CountDownLatch latch;
        private final String serviceName;
        
        Service(CountDownLatch latch, String serviceName) {
            this.latch = latch;
            this.serviceName = serviceName;
        }
        
        @Override
        public void run() {
            try {
                // 模拟服务启动
                System.out.println(serviceName + " 正在启动~~~");
                Thread.sleep((long) (Math.random() * 3000)); // 模拟不同启动时间
                System.out.println(serviceName + " 启动完成。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }