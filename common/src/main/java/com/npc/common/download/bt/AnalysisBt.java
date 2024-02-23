package com.npc.common.download.bt;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author NPC
 * @description
 * @create 2023/8/15 22:17
 */
public class AnalysisBt {
    public static void analysis(String magnetLink) {
        // 磁力链接
//        String magnetLink = "magnet:?xt=urn:btih:909caad3ebe693efada3d1277462012e07d341f1";

        // 正则表达式
        String pattern = "magnet:\\?xt=urn:btih:([A-F0-9]+)";
//        String pattern = "magnet:\\?xt=urn:btih:([A-F0-9]+)&dn=([A-Za-z0-9.]+)";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(magnetLink);

        if (m.find()) {
            String hash = m.group(1);
            String fileName = m.group(2);

            System.out.println("Hash: " + hash);
            System.out.println("File Name: " + fileName);
        } else {
            System.out.println("Invalid magnet link");
        }
    }

    public static void download(String fileUrl,String savePath) {
        try {
            URL url = new URL(fileUrl);
            URLConnection connection = url.openConnection();

            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            FileOutputStream out = new FileOutputStream(savePath);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            in.close();

            System.out.println("File downloaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String magnetLink = "magnet:?xt=urn:btih:909caad3ebe693efada3d1277462012e07d341f1&dn=example.torrent";
        String savePath = "/path/to/save/file.torrent";

        // 解析磁力链
        String pattern = "magnet:\\?xt=urn:btih:([A-F0-9]+)&dn=([A-Za-z0-9.]+)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(magnetLink);

        if (m.find()) {
            String hash = m.group(1);
            String fileName = m.group(2);

            // 下载文件
            String fileUrl = "" + fileName;
            try {
                URL url = new URL(fileUrl);
                URLConnection connection = url.openConnection();

                BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
                FileOutputStream out = new FileOutputStream(savePath);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }

                out.close();
                in.close();

                System.out.println("File downloaded successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid magnet link");
        }
    }
}
