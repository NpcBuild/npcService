package com.npc.common.file.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author NPC
 * @description
 * @create 2023/3/12 18:01
 */
@Slf4j
public class FileNormalOperation {

    public static void aloneVideoPlay(HttpServletRequest request, HttpServletResponse response, String floderPath, String fileName) {
        InputStream is = null;
        OutputStream os = null;
        try {
            response.setContentType("video/mp4");
            File file = new File(floderPath + fileName);
            response.addHeader("Content-Length", "" + file.length());
            is = new FileInputStream(file);
            os = response.getOutputStream();
            IOUtils.copy(is, os);
        } catch (Exception e) {
            log.error("播放MP4失败", e);
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
