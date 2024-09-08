package com.npc.core.net.mulitDown;

import com.npc.core.net.MineRestTemplateBuilder;
import com.npc.core.net.extractor.FileResponseExtractor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author NPC
 * @description 如果服务器不限速的话，通常能够把自己本地的带宽给跑满，那么使用单线程下载就够了，但是如果遇到服务器限速，下载速度远小于自己本地的带宽，那么可以考虑使用多线程下载
 * 使用CompletableFuture
 * 1.首先通过Http协议的Head方法获取到文件的总大小
 * 2.然后根据设置的线程数均分文件的大小，计算每个线程的下载的字节数据开始位置和结束位置
 * 3.开启线程，设置HTTP请求头Range信息，开始下载数据到临时文件
 * 4.下载完成后把每个线程下载完成的临时文件合并成一个文件
 * @create 2023/8/8 18:00
 */
@Component
public class MulitDown {
    @Resource
    private MineRestTemplateBuilder templateBuilder;
    public void multiThreadDownload(String fileURL, String filePath, int threadNum) throws IOException {
        System.out.println("开始下载文件。。..");
        ExecutorService executorService = Executors. newFixedThreadPool(threadNum);

        long startTime = System. currentTimeMillis();

        //通过Http协议的Head方法获取到文件的总大小
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers) ;
        RestTemplate restTemplate = templateBuilder.build();
        ResponseEntity<String> entity = restTemplate.exchange(fileURL, HttpMethod.HEAD, requestEntity, String.class);

        long contentLength = entity.getHeaders().getContentLength();
        // 均分文件大小
        long step = contentLength / threadNum;

        List<CompletableFuture<File>> futures = new ArrayList<>();
        for (int index = 0; index < threadNum; index++) {
            // 计算出每个线程的下载开始位置和结束位置
            String start = step * index + "";
            String end = index == threadNum - 1? "": (step * (index + 1) - 1) + "";
            FileResponseExtractor extractor = new FileResponseExtractor(filePath + " download." + index);
            CompletableFuture<File> future = CompletableFuture.supplyAsync(() -> {
                RequestCallback callback = request -> {
                    //设置HTTP请求头Range信息，开始下载到临时文件
                    request.getHeaders().add(HttpHeaders.RANGE, "bytes=" + start + "-" + end);
                };
                return (File)restTemplate.execute(fileURL, HttpMethod.GET, callback, extractor);
            }, executorService).exceptionally(e->{
//                e.printStackTrace();
                return null;
            });
            futures.add(future);
}
            //创建最终文件
            FileChannel outChannel = new FileOutputStream(new File(filePath)).getChannel() ;
            futures.forEach( future -> {
                try {
                    File tmpFile = future.get();
                    FileChannel tmpIn = new FileInputStream(tmpFile).getChannel();
                    // 合并每个临时文件
                    outChannel.transferFrom(tmpIn, outChannel.size(), tmpIn.size());
                    tmpIn.close();
                    tmpFile.delete(); //合并完成后删除临时文件
                } catch (InterruptedException | ExecutionException | IOException e) {
                    e.printStackTrace();
                }
            });
            outChannel.close();
            executorService.shutdown();
            System.out.println("下载文件完成，总共耗时：" + (System.currentTimeMillis() - startTime) / 1000 + "s");
    }
}
