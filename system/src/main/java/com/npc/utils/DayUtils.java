package com.npc.utils;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @program: npcService
 * @description 日期工具类
 * @author: feiyang
 * @create: 2025/12/06 11:08
 **/
public class DayUtils {

    // 公历转农历
    public static String gregorianToLunar(String gregorianDate) {
        Date date = new Date();
        if (StringUtils.isNotBlank(gregorianDate)) {
            date = DateUtil.parse(gregorianDate);
        }
        ChineseDate chineseDate = new ChineseDate(date);
        // 获取农历年月日的数值
        int year = chineseDate.getChineseYear();
        int month = chineseDate.getMonth();
        int day = chineseDate.getDay();

        // 返回标准格式 YYYY-MM-DD
        return String.format("%d-%02d-%02d", year, month, day);
    }

    // 农历转公历 农历yyyy-mm-dd转公历yyyy-mm-dd
    // 如果传递的是mm-dd格式的日期，则找当年对应的农历为mm-dd的阳历日期
    public static String lunarToGregorian(String lunarDate, Integer returnYear) {
        if (StringUtils.isBlank(lunarDate)) {
            return null;
        }

        try {
            int year, month, day;

            // 解析农历日期格式 YYYY-MM-DD 或 MM-DD
            String[] parts = lunarDate.split("-");
            if (parts.length == 3) {
                // YYYY-MM-DD 格式 - 直接使用指定年份
                year = Integer.parseInt(parts[0]);
                month = Integer.parseInt(parts[1]);
                day = Integer.parseInt(parts[2]);
            } else if (parts.length == 2) {
                // MM-DD 格式 - 使用当前年份查找对应的农历日期
                year = java.time.LocalDate.now().getYear();
                month = Integer.parseInt(parts[0]);
                day = Integer.parseInt(parts[1]);
            } else {
                throw new IllegalArgumentException("Invalid lunar date format, expected YYYY-MM-DD or MM-DD");
            }

            year = (returnYear == null) ? year : returnYear;
            // 使用 Hutool 的 ChineseDate 类将农历转换为公历
            ChineseDate chineseDate = new ChineseDate(year, month, day);
            Date gregorianDate = chineseDate.getGregorianDate();

            // 格式化为 YYYY-MM-DD 格式
            return DateUtil.format(gregorianDate, "yyyy-MM-dd");
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert lunar date to gregorian date: " + lunarDate, e);
        }
    }

}
