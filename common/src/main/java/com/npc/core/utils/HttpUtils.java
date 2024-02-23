package com.npc.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author NPC
 * @description
 * @create 2023/10/15 10:24
 */
public class HttpUtils {
    public static Boolean includeURL(String content) {
        Boolean res = false;

        String pattern = "https?://\\S+"; // 正则表达式用于匹配URL
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(content);
        while (m.find()) {
            System.out.println("Found URL: " + m.group());
            res = true;
        }

        if (!res) {
            System.out.println("No URL found.");
        }
        return res;
    }
}
