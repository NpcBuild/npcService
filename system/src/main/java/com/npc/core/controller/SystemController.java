package com.npc.core.controller;

import cn.hutool.core.date.ChineseDate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.npc.core.ServerResponseVO;
import com.npc.core.net.query.Workday;
import com.npc.utils.DateUtils;
import com.npc.utils.DayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author NPC
 * @description
 * @create 2024/10/15 21:19
 */
@CrossOrigin
@RestController
@RequestMapping("/sys")
public class SystemController {

    @GetMapping("/specialDay")
    public boolean specialDay(String date) {
        LocalDate localDate = LocalDate.now();
        if (StringUtils.isNotEmpty(date)) {
            localDate = LocalDate.parse(date);
        }
        return Workday.isSpecialDay(localDate);
    }
    @GetMapping("/workDay")
    public boolean workDay(String date) {
        LocalDate localDate = LocalDate.now();
        if (StringUtils.isNotEmpty(date)) {
            localDate = LocalDate.parse(date);
        }
        return Workday.isWorkday(localDate);
    }
    @GetMapping("/dayInfo")
    public ServerResponseVO<?> dayInfo(String date) {
        LocalDate localDate = LocalDate.now();
        if (StringUtils.isNotEmpty(date)) {
            localDate = LocalDate.parse(date);
        }
        Map<String, Object> res = new HashMap<>();
        // 阳历日期
        res.put("gregorianDate", localDate.toString());
        // 农历日期
        res.put("lunarDay", DayUtils.gregorianToLunar(date));
        res.put("dateTime", DateUtils.getTime());

        // 星期几
        res.put("dayOfWeek", localDate.getDayOfWeek().toString());
        res.put("dayOfWeekChinese", getChineseDayOfWeek(localDate.getDayOfWeek()));

        // 日期基本信息
        res.put("year", localDate.getYear());
        res.put("month", localDate.getMonthValue());
        res.put("day", localDate.getDayOfMonth());

        res.put("workday", Workday.isWorkday(localDate));
        res.put("specialDay", Workday.isSpecialDay(localDate));
        return ServerResponseVO.success(res);
    }

    /**
     * 获取星期的中文表示
     */
    private String getChineseDayOfWeek(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY: return "星期一";
            case TUESDAY: return "星期二";
            case WEDNESDAY: return "星期三";
            case THURSDAY: return "星期四";
            case FRIDAY: return "星期五";
            case SATURDAY: return "星期六";
            case SUNDAY: return "星期日";
            default: return "";
        }
    }

    @GetMapping("/weather")
    public ServerResponseVO<?> weather(String city) {
        try {
            // 默认使用北京的经纬度坐标
            String latitude = "39.92604438915782";
            String longitude = "116.6071179076529";

            // 构造天气API请求URL
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
                    "&longitude=" + longitude +
                    "&current=temperature_2m,wind_speed_10m,precipitation,rain,showers,snowfall,weather_code" +
                    "&daily=temperature_2m_max,temperature_2m_min,precipitation_sum,rain_sum,showers_sum,snowfall_sum,weather_code" +
                    "&timezone=Asia/Shanghai";

            // 使用RestTemplate发起HTTP请求
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // 解析返回的JSON数据
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());

            // 获取当前天气信息
            JsonNode current = root.path("current");
            double currentTemperature = current.path("temperature_2m").asDouble();
            double windSpeed = current.path("wind_speed_10m").asDouble();

            // 获取今日天气预报信息
            JsonNode daily = root.path("daily");
            double maxTemperature = daily.path("temperature_2m_max").get(0).asDouble();
            double minTemperature = daily.path("temperature_2m_min").get(0).asDouble();
            double precipitationSum = daily.path("precipitation_sum").get(0).asDouble();
            double rainSum = daily.path("rain_sum").get(0).asDouble();
            double showersSum = daily.path("showers_sum").get(0).asDouble();
            double snowfallSum = daily.path("snowfall_sum").get(0).asDouble();

            // 获取当前日期
            String currentDate = DateUtils.getDate();

            // 构造返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("date", currentDate);
            result.put("city", "北京");
            result.put("currentTemperature", currentTemperature + "°C");
            result.put("temperatureRange", minTemperature + "°C ~ " + maxTemperature + "°C");
            result.put("windSpeed", windSpeed + " km/h");

            // 判断异常天气
            StringBuilder weatherCondition = new StringBuilder();
            if (precipitationSum > 0) {
                if (snowfallSum > 0) {
                    weatherCondition.append("有雪 ");
                }
                if (rainSum > 0 || showersSum > 0) {
                    weatherCondition.append("有雨 ");
                }
            } else {
                weatherCondition.append("晴朗 ");
            }

            result.put("weatherCondition", weatherCondition.toString().trim());
            result.put("precipitation", precipitationSum + " mm");

            return ServerResponseVO.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error("获取天气信息失败: " + e.getMessage());
        }
    }
}
