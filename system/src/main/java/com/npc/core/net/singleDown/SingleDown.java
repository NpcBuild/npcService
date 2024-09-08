package com.npc.core.net.singleDown;

import com.npc.core.net.MineRestTemplateBuilder;
import com.npc.core.net.extractor.ByteArrayResponseExtractor;
import com.npc.core.net.extractor.FileResponseExtractor;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author NPC
 * @description 单线程下载
 * @create 2024/4/20 16:43
 */
public class SingleDown {
    private static final String testUrl = "https://image.baidu.com/search/down?url=https://tvax2.sinaimg.cn/large/006BNqYCly1hgfpxtpnz4j30j6159tfv.jpg";
    private static final String filePath = "D:\\Data\\tmp2.png";
    @Resource
    private MineRestTemplateBuilder restTemplateBuilder;
    public void downloadToFile(String url, String filePath) throws IOException {
        long start = System.currentTimeMillis();
        RestTemplate restTemplate = restTemplateBuilder.build();
        FileResponseExtractor extractor = new FileResponseExtractor(filePath + ".download"); // 创建临时下载文件
        File tmpFile = (File) restTemplate.execute(url, HttpMethod.GET,null,extractor);
        tmpFile.renameTo(new File(filePath));
        System.out.println("总共下载文件耗时：" + (System.currentTimeMillis() - start) / 1000 + "S");
    }

    public void downloadToMemory(String url, String filePath) throws IOException {
        long start = System.currentTimeMillis();
        RestTemplate restTemplate = restTemplateBuilder.build();
        byte[] body = restTemplate.execute(url, HttpMethod.GET,null,new ByteArrayResponseExtractor());
        Files.write(Paths.get(filePath), Objects.requireNonNull(body));
        System.out.println("总共下载文件耗时：" + (System.currentTimeMillis() - start) / 1000 + "S");
    }
}
