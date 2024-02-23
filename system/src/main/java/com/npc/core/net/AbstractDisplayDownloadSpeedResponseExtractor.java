package com.npc.core.net;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

import java.io.IOException;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

/**
 * @author NPC
 * @description 当客户端和服务器端连接建立之后，会调用这个方法，我们可以在这个方法中监控下载的速度
 * @create 2023/8/7 19:18
 */
public abstract class AbstractDisplayDownloadSpeedResponseExtractor<T> implements ResponseExtractor<T>, DisplayDownloadSpeed {
    @Override
    public T extractData(ClientHttpResponse response) throws IOException {
        long contentLength = response.getHeaders().getContentLength();
        this.displaySpeed(Thread.currentThread().getName(), contentLength);
        return this.doExtractData(response);
    }

    protected abstract T doExtractData(ClientHttpResponse response) throws IOException;

    protected abstract long getAlreadyDownloadLength();

    @Override
    public void displaySpeed(String task, long contentLength) {
        long totalSize = contentLength / 1024;
        CompletableFuture.runAsync(() -> {
            long tmp = 0, speed;
            while (contentLength - tmp > 0) {
                speed = getAlreadyDownloadLength() - tmp;
                tmp = getAlreadyDownloadLength();
                print(task, totalSize, tmp, speed);
                sleep();
            }
        });
    }

    protected void print(String task, long totalSize, long tmp, long speed) {
        System.out.println(task + " 文件总大小： " + totalSize + "KB，已下载：" + (tmp / 1024) + "KB，下载速度：" + (speed / 1000) + "KB");
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
