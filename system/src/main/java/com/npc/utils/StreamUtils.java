package com.npc.utils;

import javax.servlet.ServletInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author NPC
 * @description
 * @create 2024/6/22 17:07
 */
public class StreamUtils {
    public static String inputStream2String(ServletInputStream inputStream, String encoding) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        byte[] data = outputStream.toByteArray();
        return new String(data, encoding);
    }
}
