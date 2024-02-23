package com.npc.core.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author NPC
 * @description 生成二维码的工具类
 * @create 2023/3/3 23:37
 */
public class QRCodeUtils {
    /**
     *  生成二维码
     * @param content 二维码的内容
     * @return BitMatrix对象
     * */
    public static BitMatrix createCode(Map<String, String> content) throws IOException {
        //二维码的宽高
        int width = 200;
        int height = 200;

        //其他参数，如字符集编码
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //容错级别为H
        hints.put(EncodeHintType.ERROR_CORRECTION , ErrorCorrectionLevel.H);
        //白边的宽度，可取0~4
        hints.put(EncodeHintType.MARGIN , 0);

        BitMatrix bitMatrix = null;
        try {
            //生成矩阵，
            bitMatrix = new MultiFormatWriter().encode(String.valueOf(content),
                    BarcodeFormat.QR_CODE, width, height, hints);

            //bitMatrix = deleteWhite(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return bitMatrix;
    }

    /**
     * 创建 二维码所需图片
     * @param content 内容
     * @return
     * @throws Exception
     */
    public static BufferedImage createImage(Map<String, String> content, String bottomDes) throws Exception {
        Hashtable hints = new Hashtable();
        int width = 200;
        int height = 200;
        //容错级别 H->30%
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(String.valueOf(content),
                BarcodeFormat.QR_CODE, width, height, hints);
        int tempHeight = height;
        boolean needDescription=(null!=bottomDes&&!"".equals(bottomDes));
        if (needDescription) {
            tempHeight += 30;
        }
        BufferedImage image = new BufferedImage(width, tempHeight,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        addFontImage(image, bottomDes);
        //========同理添加logo=====现在不需添加logo
        return image;
    }

    /**
     * 添加 底部图片文字
     * @param source 图片源
     * @param declareText 文字本文
     */
    private static void addFontImage(BufferedImage source, String declareText) {
        Graphics2D graph = source.createGraphics();
        graph.setColor(Color.white);
        graph.drawString(declareText,20,420);
        graph.dispose();
    }



    /**
     *  删除生成的二维码周围的白边，根据审美决定是否删除
     * @param matrix BitMatrix对象
     * @return BitMatrix对象
     * */
    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }
    /**
     * 插入Logo图片
     * @param source 图片操作对象
     * @param logoImage Logo图片地址
     * @param needCompress 是否压缩Logo大小
     * @throws Exception
     */
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;
    // 二维码尺寸
    private static final int QRCODE_SIZE = 200;
    private static void insertImage(BufferedImage source,BufferedImage logoImage,
                                    boolean needCompress) throws Exception {
        int width = logoImage.getWidth(null);
        int height = logoImage.getHeight(null);

        Image src =logoImage;
        if (needCompress) {
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }

            Image image = logoImage.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }
}
