package com.npc.core.encrypt.base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

/**
 * @author NPC
 * @description
 * @create 2024/3/12 20:50
 */
public class ImageUtil {

    /**
     * 图片转Base64字符串
     *
     * @param imageFileName
     * @return
     */
    public static String convertImageToBase64Str(String imageFileName) {
        ByteArrayOutputStream baos = null;
        try {
            //获取图片类型
            String suffix = imageFileName.substring(imageFileName.lastIndexOf(".") + 1);
            //构建文件
            File imageFile = new File(imageFileName);
            //通过ImageIO把文件读取成BufferedImage对象
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            //构建字节数组输出流
            baos = new ByteArrayOutputStream();
            //写入流
            ImageIO.write(bufferedImage, suffix, baos);
            //通过字节数组流获取字节数组
            byte[] bytes = baos.toByteArray();
            //获取JDK8里的编码器Base64.Encoder转为base64字符
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * Base64字符串转图片
     * @param base64String
     * @param imageFileName
     */
    public static void convertBase64StrToImage(String base64String, String imageFileName) {
        try {
            //获取JDK8里的解码器Base64.Decoder,将base64字符串转为字节数组
            byte[] bytes = Base64.getDecoder().decode(base64String);

            writeByteTo(bytes, imageFileName, "2");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 把字节流输出为文件的两种形式
     * @param bytes 字节流
     * @param filePath 文件路径/文件名
     * @param type 运行模式
     * @throws IOException
     */
    private static void writeByteTo(byte[] bytes, String filePath, String type) throws IOException {
        if ("1".equals(type)) {
            ByteArrayInputStream bais = null;
            try {
                //获取图片类型
                String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
                //构建字节数组输入流
                bais = new ByteArrayInputStream(bytes);
                System.out.println("获取字节流");
                //通过ImageIO把字节数组输入流转为BufferedImage
                BufferedImage bufferedImage = ImageIO.read(bais);
                //构建文件
                File imageFile = new File(filePath);
                System.out.println("构建文件" + filePath);
                //写入生成文件
                ImageIO.write(bufferedImage, suffix, imageFile);
                System.out.println("写入生成文件" + imageFile);
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            // 写入文件
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(bytes);
                fos.flush();
                System.out.println("图片已保存到: " + filePath);
            }
        }
    }

    public static void main(String[] args) {
        String base = "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";
        convertBase64StrToImage(base, "aaa.jpg");
//        String base64Str = convertImageToBase64Str("C:\\Users\\NPC\\Desktop\\test.jpg");
//        System.out.println(base64Str);
    }


}
