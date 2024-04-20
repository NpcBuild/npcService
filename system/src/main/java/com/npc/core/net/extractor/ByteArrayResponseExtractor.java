package com.npc.core.net.extractor;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author NPC
 * @description
 * @create 2023/8/7 19:31
 */
public class ByteArrayResponseExtractor extends AbstractDisplayDownloadSpeedResponseExtractor<byte[]> {
    private long byteCount; // 保存已经下载的字节数

    @Override
    protected byte[] doExtractData(ClientHttpResponse response) throws IOException {
        long contentLength = response.getHeaders().getContentLength();
        ByteArrayOutputStream out = new ByteArrayOutputStream(contentLength >= 0 ? (int)contentLength : StreamUtils.BUFFER_SIZE);
        InputStream in = response.getBody();
        int bytesRead;
        //循环读取数据到字节数组中，记录以及下载的字节数
        for (byte[] buffer = new byte[4096]; (bytesRead = in.read(buffer)) != -1; byteCount += bytesRead) {
            out.write(buffer, 0, bytesRead);
        }
        out.flush();
        return out.toByteArray();
    }

    //返回已经下载的字节数
    @Override
    protected long getAlreadyDownloadLength() {
        return byteCount;
    }
}
