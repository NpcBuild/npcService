package com.npc.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NPC
 * @description 把txt文件解析成json对象
 * @create 2024/4/22 19:16
 */
public class TxtToJsonParser {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\NPC\\Desktop\\waibao.txt";
        parseJsonFromTxt(filePath);
    }

    private static void parseJsonFromTxt(String filePath) {
        try {
//            读取文件内容
            String text = readFile(filePath);
//            解析
            JSONObject obj = JSON.parseObject(text);
            List use = new ArrayList<>();
//            处理
            JSONArray array = (JSONArray) obj.get("root");
            int i = 1;
            for (Object o : array) {
                JSONObject jo = (JSONObject) o;
//                String unit = jo.get("personUnit").toString();
                String school = jo.get("school").toString();
                String major = jo.get("major").toString();
                String name = jo.get("name").toString();
//                if (name.equals("张畅")) {
//                    System.out.println("111");
//                }
                JSONObject wbSupplier = (JSONObject)jo.get("wbSupplier");
                String unit = wbSupplier.get("name").toString();
                String phone = jo.get("phone").toString();
                JSONObject sGender = (JSONObject)jo.get("sGender");
                String gender = sGender.get("name").toString();
                Integer age = Integer.valueOf(jo.get("age").toString());
                JSONObject user = (JSONObject)jo.get("user");
                String id = "";
                if (user != null && user.get("id") != null) {
                    id = user.get("id").toString();
                }
                String workYear = jo.get("workYear").toString();
                String bornTime = jo.get("bornTime").toString();
                JSONObject swbOutsourcedFaceResult = (JSONObject)jo.get("swbOutsourcedFaceResult");
                String swbOutsourcedFaceResultName = "";
                if (swbOutsourcedFaceResult != null) {
                    swbOutsourcedFaceResultName = swbOutsourcedFaceResult.get("name").toString();
                }
                String s = i++ + "," + id + "," +name+","+gender+","+age+","+unit+","+workYear+","+bornTime+","+school+","+major+","+","+phone+","+swbOutsourcedFaceResultName;
//                System.out.println(s);
                if ("女".equals(gender) && (age>20 && age<30)) {
                    use.add(s);
                }
            }
            System.out.println("结果为：");
            for (Object o : use) {
                System.out.println(o.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String filePath) {
        String all = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine())!= null) {
//                System.out.println(line);
                all += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return all;
    }
}
