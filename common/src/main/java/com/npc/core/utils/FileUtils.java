package com.npc.core.utils;

import sun.awt.shell.ShellFolder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

/**
 * @author NPC
 * @description
 * @create 2023/8/5 10:55
 */
public class FileUtils {

    /**
     * 获取小图标
     * @param file
     * @return
     */
    public static Icon GetSmallIcon(File file) {
        if ( file != null && file.exists() ) {
            FileSystemView fsv = FileSystemView.getFileSystemView();
            return fsv.getSystemIcon(file);
        }
        return null;
    }
    /**
     * 获取大图标
     * @param file
     * @return
     */
    public static Icon GetBigIcon(File file) {
        if ( file != null && file.exists() ) {
            try {
                ShellFolder shellFolder = ShellFolder.getShellFolder(file);
                return new ImageIcon(shellFolder.getIcon(true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取文件目录大小
     * @param folder
     * @return
     */
    public static long getFolderSize(File folder) {
        long size = 0;
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        size += file.length();
                    } else {
                        size += getFolderSize(file);
                    }
                }
            }
        } else if (folder.isFile()) {
            size = folder.length();
        }
        return size;
    }

    /**
     * 压缩图片方法
     * @param yFilePath 原图片路径
     * @param width 压缩宽
     * @param height 压缩高
     * @param quality 压缩清晰度 <b>建议为1.0</b>
     * @param outFile 压缩图片后,图片名称路径
     * @param percentage 是否等比压缩 若true宽高比率将将自动调整
     * @return
     */
    public static String doCompress(String yFilePath, int width, int height, float quality, String outFile, boolean percentage) {
        if (yFilePath != null && width > 0 && height > 0) {
            Image srcFile = null;
            try {
                File file = new File(yFilePath);
                // 文件不存在
                if (!file.exists()) {
                    return null;
                }
                /*读取图片信息*/
                srcFile = ImageIO.read(file);
                int new_w = width;
                int new_h = height;
                if (percentage) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) srcFile.getWidth(null)) / (double) width + 0.1;
                    double rate2 = ((double) srcFile.getHeight(null)) / (double) height + 0.1;
                    double rate = rate1 > rate2 ? rate1 : rate2;
                    new_w = (int) (((double) srcFile.getWidth(null)) / rate);
                    new_h = (int) (((double) srcFile.getHeight(null)) / rate);
                }
                /* 宽高设定*/
                BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
                tag.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null);

                /*压缩之后临时存放位置*/
                FileOutputStream out = new FileOutputStream(outFile);

                ImageIO.write(tag, "JPG", out);
                out.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                srcFile.flush();
            }
            return outFile;
        } else {
            return null;
        }
    }

    /**
     * 获取文件缩略图
     * @param  /** int thumbnailWidth, int thumbnailHeight
     * @return
     */
    public static String thumbnail(String yFilePath) {
        return thumbnail(yFilePath,0,0);
    }
    public static String thumbnail(String yFilePath, int width, int height) {
        if (yFilePath != null) {
            File file = new File(yFilePath);
            try {
                InputStream inputStream = new FileInputStream(file);
                BufferedImage originalImage = ImageIO.read(inputStream);
                if (width == 0 && height == 0) {
                    width = 600;
                    height = originalImage.getHeight() * width / originalImage.getWidth();
                }
                BufferedImage thumbnailImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

                Graphics2D graphics2D = thumbnailImage.createGraphics();
                graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                graphics2D.drawImage(originalImage, 0, 0, width, height, null);
                graphics2D.dispose();

                // 将缩略图转换为Base64编码的字符串并返回
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(thumbnailImage, "jpg", outputStream);
                byte[] bytes = outputStream.toByteArray();
                outputStream.close();
                return Base64.getEncoder().encodeToString(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
