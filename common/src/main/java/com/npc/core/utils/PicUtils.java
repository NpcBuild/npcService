package com.npc.core.utils;

import java.io.File;

/**
 * @program: npcService
 * @description 图片工具类
 * @author: feiyang
 * @create: 2025/11/06 22:58
 **/
public class PicUtils {

    /**
     * 检查目录下是否有图片文件
     * @param directory 目录
     * @return 是否有图片文件
     */
    public static boolean hasImageFiles(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName().toLowerCase();
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
                            fileName.endsWith(".png") || fileName.endsWith(".gif") ||
                            fileName.endsWith(".bmp")) {
                        return true;
                    }
                } else if (file.isDirectory()) {
                    // 递归检查子目录
                    if (hasImageFiles(file)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
