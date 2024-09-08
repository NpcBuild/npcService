package com.npc.utils.watermark;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author NPC
 * @description
 * @create 2024/7/4 18:29
 */
public class ImageWatermarker {
    public static void main(String[] args) {
        try {
            String watermarkText = "水印文字";
            // 读取原始图片
            BufferedImage originalImage = ImageIO.read(new File("path_to_your_original_image.jpg"));
            // 创建一个新的BufferedImage，作为水印图片
            BufferedImage watermarkImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            // 获取Graphics2D对象以绘制水印图片
            Graphics2D g2d = watermarkImage.createGraphics();
            // 设置字体和颜色
            Font font = new Font("宋体", Font.PLAIN, 50); // 使用宋体字体
            g2d.setFont(font);
            g2d.setColor(Color.WHITE);
            // 在水印图片上写入文字
            String text = new String("水印文字".getBytes(), StandardCharsets.UTF_8); // 使用UTF-8 编码创建字符串解决水印中文乱码问题
            int x = originalImage.getWidth() - g2d.getFontMetrics().stringWidth(watermarkText) - 30; // 右下角位置的x坐标
            int y = originalImage.getHeight() - 30; // 右下角位置的y坐标
            g2d.drawString(watermarkText, x, y);
            g2d.dispose();
            // 将原始图片和水印图片合并
            Graphics g = originalImage.getGraphics();
            g.drawImage(watermarkImage, 0, 0, null);
            g.dispose();
            // 将结果图片写入文件
            File outputFile = new File("path_to_your_output_image.jpg");
            ImageIO.write(originalImage, "jpg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
