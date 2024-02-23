 package com.npc.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author NPC
 * @description 正则表达式工具类
 * @create 2023/5/2 13:27
 */
public class RegularUtils {
    /**
     * test
     * Pattern.compile("^mch(\d{8})$")：创建一个正则表达式的 Pattern 对象，该表达式匹配以 "mch" 开头，后面是8位数字的字符串，并把8位数字部分放在一个分组中。
     * matcher.find()：使用正则表达式匹配字符串。
     * matcher.group()：获取匹配的字符串。
     * matcher.group(1)：获取匹配的数字部分
     */
    public void test() {
        String str = "mch12345678";
        Pattern pattern = Pattern.compile("^mch(\\d{8})$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            String matchedString = matcher.group(); // 匹配的字符串
            String digits = matcher.group(1); // 匹配的数字
            System.out.println("匹配的字符串为：" + matchedString);
            System.out.println("匹配的数字为：" + digits);
        } else {
            System.out.println("字符串不符合要求");
        }
    }
}
