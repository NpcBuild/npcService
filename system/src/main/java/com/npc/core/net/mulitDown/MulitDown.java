package com.npc.core.net.mulitDown;

import com.npc.core.net.RestTemplateBuilder;
import com.npc.core.net.singleDown.FileResponseExtractor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

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
 * @description
 * @create 2023/8/8 18:00
 */
public class MulitDown {
    public void multiThreadDownload(String fileURL, String filePath, int threadNum) throws IOException {
        System.out.println("开始下载文件。。..");
        ExecutorService executorService = Executors. newFixedThreadPool (threadNum);
        long startTime = System. currentTimeMillis();
        //通过Http协议的Head方法获取到文件的总大小
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers) ;
        RestTemplateBuilder templateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = templateBuilder.build();
        ResponseEntity<String> entity = restTemplate.exchange(fileURL, HttpMethod.HEAD, requestEntity, String.class);
        long contentLength = entity.getHeaders ( ) . getContentLength() ;
        long step = contentLength / threadNum;
        List<CompletableFuture<File>> futures = new ArrayList<>();
        for (int index = 0; index < threadNum; index++) {
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
