package com.npc.common.pdf.utils;

import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * @author dume
 * @ClassName PdfTest
 * @description: pdf模板操作
 * @date 2024年07月26日
 * @version: 1.0
 */
public class PdfTest {

    public static void main(String[] args) {
        //测试执行
        FillTemplate(
                "D:\\Code\\Mine\\npcService\\common\\src\\main\\java\\com\\npc\\common\\pdf\\utils\\申请书.pdf",
                "D:\\Code\\Mine\\npcService\\common\\src\\main\\java\\com\\npc\\common\\pdf\\utils\\新申请书.pdf",
                "杜小七",
                "辽宁大连",
                "跑步",
                "Yes",
                "Yes",
                "Yes",
                "C:\\test\\电子签名.png",
                "C:\\test\\公章.png"
        );
    }

    /**
     * 根据模板生成pdf
     * @param sourcesPath 原文件路径
     * @param targetPath  生成文件路径
     * @param name     参数
     * @param address  参数
     * @param hobby    参数
     * @param select_1  选项
     * @param select_2  选项
     * @param select_3  选项
     * @param signPath  签名图片路径
     * @param gongzhangPath  公章图片路径
     */
    public static void FillTemplate(
            String sourcesPath,
            String targetPath,
            String name,
            String address,
            String hobby,
            String select_1,
            String select_2,
            String select_3,
            String signPath,
            String gongzhangPath
            ){

        //设置参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("address",address);
        jsonObject.put("hobby",hobby);
        jsonObject.put("select_1",select_1);
        jsonObject.put("select_2",select_2);
        jsonObject.put("select_3",select_3);
        // 填充创建pdf
        PdfReader reader = null;
        PdfStamper stamp = null;
        ByteArrayOutputStream baos =null;
        try {
            reader = new PdfReader( sourcesPath);
            File deskFile = new File(targetPath);
            stamp = new PdfStamper(reader, new FileOutputStream(deskFile));
            // 取出报表模板中的所有字段
            AcroFields form = stamp.getAcroFields();
            System.out.println(form.getFields().keySet());


            //设置宋体
            BaseFont song =BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            if (jsonObject != null) {
                for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue().toString();
                    //保存选项
                    if (key.startsWith("select")) {
                        form.setField(key, value, true);
                    //保存文字
                    }else {
                        form.setFieldProperty(key, "textfont", song, null);
                        form.setField(key, value);
                    }
                }
            }

            //插入签名
            insertImage(form,stamp,"sign",signPath);
            //插入公章
            insertImage(form,stamp,"gongzhang",gongzhangPath);
            //保存修改
            stamp.setFormFlattening(true);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (stamp != null) {
                try{
                    stamp.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            if (reader != null) {
                try{
                    reader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try{
                    baos.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }

    }

    /**
     * pdf模板插入图片
     * @param form
     * @param stamper
     * @param filedName
     * @param url
     * @return
     */
    public  static boolean insertImage(AcroFields form, PdfStamper stamper, String filedName, String url) {
        try {
            int pageNo = form.getFieldPositions(filedName).get(0).page;
            Rectangle signRect = form.getFieldPositions(filedName).get(0).position;
            float x = signRect.getLeft();
            float y = signRect.getBottom();

            Image image = Image.getInstance(url);
            // 获取操作的页面
            PdfContentByte under = stamper.getOverContent(pageNo);
            // 根据域的大小缩放图片
            image.scaleToFit(signRect.getWidth(), signRect.getHeight());
            // 添加图片
            image.setAbsolutePosition(x, y);
            under.addImage(image);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}