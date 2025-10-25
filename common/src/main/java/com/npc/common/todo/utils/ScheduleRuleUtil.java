package com.npc.common.todo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npc.common.todo.dto.ScheduleRuleTextInput;
import com.npc.common.todo.entity.Todo;
import com.fasterxml.jackson.core.type.TypeReference;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author NPC
 * @description
 * @create 2025/5/17 21:12
 */
public class ScheduleRuleUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /*
      | repeat_type      | repeat_value              | 输出       |
      | ----------------- | -------------------------- | -------- |
      | `yearly_month`    | `{"month":5,"day":17}`     | 每年5月17日  |
      | `monthly_weekday` | `{"week":2,"weekday":3}`   | 每月第2个星期三 |
      | `every_x_days`    | `{"interval":3}`           | 每3天一次    |
      | `weekly`          | `{"days":[1,3,5]}`         | 每周一三五    |
      | `ebbinghaus`      | `{"days":[1,2,4,7,15,30]}` | 艾宾浩斯记忆法  |
     */

    /**
     * 将中文字符串规则解析为结构化存储对象
     */
    public static Todo parse(ScheduleRuleTextInput input) {
        String ruleText = input.getRuleText();
        String repeatType;
        Map<String, Object> repeatValue = new HashMap<>();

        if (ruleText.matches("每年\\d{1,2}月\\d{1,2}日")) {
            repeatType = "yearly_month";
            Matcher m = Pattern.compile("每年(\\d{1,2})月(\\d{1,2})日").matcher(ruleText);
            if (m.find()) {
                repeatValue.put("month", Integer.parseInt(m.group(1)));
                repeatValue.put("day", Integer.parseInt(m.group(2)));
            }
        } else if (ruleText.matches("每月第\\d个星期[一二三四五六日天]")) {
            repeatType = "monthly_weekday";
            Matcher m = Pattern.compile("每月第(\\d)个星期([一二三四五六日天])").matcher(ruleText);
            if (m.find()) {
                repeatValue.put("week", Integer.parseInt(m.group(1)));
                repeatValue.put("weekday", chineseWeekdayToNumber(m.group(2)));
            }
        } else if (ruleText.matches("每\\d+天一次")) {
            repeatType = "every_x_days";
            Matcher m = Pattern.compile("每(\\d+)天一次").matcher(ruleText);
            if (m.find()) {
                repeatValue.put("interval", Integer.parseInt(m.group(1)));
            }
        } else if (ruleText.matches("每周[一二三四五六日天]+")) {
            repeatType = "weekly";
            Matcher m = Pattern.compile("每周([一二三四五六日天]+)").matcher(ruleText);
            if (m.find()) {
                int[] days = m.group(1).chars()
                        .mapToObj(c -> chineseWeekdayToNumber(String.valueOf((char) c)))
                        .mapToInt(Integer::intValue)
                        .toArray();
                repeatValue.put("days", Arrays.stream(days).boxed().collect(Collectors.toList()));
            }
        } else if (ruleText.contains("艾宾浩斯")) {
            repeatType = "ebbinghaus";
            repeatValue.put("days", Arrays.asList(1, 2, 4, 7, 15, 30));
        } else {
            throw new IllegalArgumentException("不支持的规则格式：" + ruleText);
        }

        try {
            Todo rule = new Todo();
            rule.setId(Integer.valueOf(input.getTaskId()));
            rule.setRecurrenceType(repeatType);
            rule.setRecurrenceDays(objectMapper.writeValueAsString(repeatValue));
            rule.setStartTime(LocalDateTime.parse(input.getStartDate()));
            rule.setEndTime(LocalDateTime.parse(input.getEndDate()));
            return rule;
        } catch (Exception e) {
            throw new RuntimeException("转换失败：" + e.getMessage(), e);
        }
    }

    /**
     * 将结构化字段转换为中文自然语言格式
     */
    public static String format(Todo rule) {
        String repeatType = rule.getRecurrenceType();
        String repeatValueJson = rule.getRecurrenceDays();

        try {
            Map<String, Object> value = objectMapper.readValue(repeatValueJson, new TypeReference<Map<String, Object>>() {});
            switch (repeatType) {
                case "yearly_month":
                    int month = (int) value.get("month");
                    int day = (int) value.get("day");
                    return String.format("每年%d月%d日", month, day);

                case "monthly_weekday":
                    int week = (int) value.get("week");
                    int weekday = (int) value.get("weekday");
                    return String.format("每月第%d个星期%s", week, numberToChineseWeekday(weekday));

                case "every_x_days":
                    int interval = (int) value.get("interval");
                    return String.format("每%d天一次", interval);

                case "weekly":
                    @SuppressWarnings("unchecked")
                    List<Integer> days = (List<Integer>) value.get("days");
                    String dayStr = days.stream()
                            .sorted()
                            .map(ScheduleRuleUtil::numberToChineseWeekday)
                            .collect(Collectors.joining(""));
                    return "每周" + dayStr;

                case "ebbinghaus":
                    return "艾宾浩斯记忆法";

                default:
                    return "未知规则";
            }
        } catch (Exception e) {
            return "解析失败：" + e.getMessage();
        }
    }

    private static int chineseWeekdayToNumber(String ch) {
        switch (ch) {
            case "一": return 1;
            case "二": return 2;
            case "三": return 3;
            case "四": return 4;
            case "五": return 5;
            case "六": return 6;
            case "日":
            case "天": return 7;
            default: throw new IllegalArgumentException("非法星期: " + ch);
        }
    }

    private static String numberToChineseWeekday(int n) {
        switch (n) {
            case 1: return "一";
            case 2: return "二";
            case 3: return "三";
            case 4: return "四";
            case 5: return "五";
            case 6: return "六";
            case 7: return "日";
            default: return "?";
        }
    }
}
