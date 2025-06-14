package com.npc.core.net.query;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.npc.common.modular.holiday.entity.Holiday;
import com.npc.common.modular.holiday.mapper.HolidayMapper;
import com.npc.common.modular.holiday.service.IHolidayService;
import com.npc.core.alarm.MessageTypes;
import com.npc.core.alarm.aop.Alarm;
import com.npc.core.net.MineRestTemplateBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.sql.SQLOutput;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author NPC
 * @description
 * apiResult rest: 调用当天距离目标剩余的天数
 * @create 2024/9/18 11:26
 */
@Component
public class Workday {

    private static JsonNode TEMP = null;
    private static List<Holiday> HOLIDAYLIST = null;
    @Autowired
    private IHolidayService holidayService;
    @Autowired
    private HolidayMapper holidayMapper;

//    @Async
//    @Scheduled(fixedDelay = 5000) // 延迟5秒后开始执行，之后每隔固定时间执行一次
    @Alarm(title = "获取节假日业务告警", messageType = MessageTypes.TEXT, templateId = "holidayErrorTemp")
    @PostConstruct
    public void init() {
        CompletableFuture.runAsync(() -> {
            try {
                System.out.println("开始获取节假日信息");
                // 获取当前日期
                LocalDate currentDate = LocalDate.now();
                // 获取当前年份
                int currentYear = currentDate.getYear();
                HttpClient client = HttpClientBuilder.create().build();
                HttpGet request = new HttpGet("https://timor.tech/api/holiday/year/" + currentYear+ "/");
                HttpResponse response = client.execute(request);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseString = EntityUtils.toString(entity);
                    ObjectMapper objectMapper = new ObjectMapper();
                    // 配置忽略未知字段
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    JsonNode jsonNode = objectMapper.readTree(responseString);
                    JsonNode holidayNode = jsonNode.get("holiday");
                    // 将 JsonNode 转换为 Map
                    Map<String, Map<String, Object>> holidayMap = objectMapper.convertValue(holidayNode, Map.class);
                    List<Holiday> holidays = new ArrayList<>();
                    for (Map<String, Object> holidayData : holidayMap.values()) {
                        Holiday holiday = objectMapper.convertValue(holidayData, Holiday.class);
                        holidays.add(holiday);
                    }
                    System.out.println("节假日信息为：" + JSON.toJSON(holidays));
                    holidayService.saveOrUpdateBatch(holidays);
                    TEMP = holidayNode;
                }
            } catch (Exception e) {
                System.out.println("请求异常" + e.getMessage());
                HOLIDAYLIST = holidayService.list();
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 判断是否特殊日（节假日或调休日）
     * @param date
     * @return
     */
    public static Boolean isSpecialDay(LocalDate date) {
        boolean holiday;
        String day = date.toString().substring(5);
        if (TEMP == null || TEMP.isEmpty()) {
            holiday = HOLIDAYLIST.stream().filter(item -> item.getDate().equals(day)).findFirst().map(Holiday::getHoliday).orElse(false);
        } else {
            JsonNode jsonNode = TEMP.get(day);
            if (jsonNode == null) {
                return null;
            }
            holiday = jsonNode.get("holiday").asBoolean();
        }
//            String day = date.getMonthValue() + "-" + date.getDayOfMonth();

        if (ObjectUtils.isEmpty(holiday)) {
            throw new RuntimeException("节假日信息获取异常");
        }
        return !holiday;
    }

    /**
     * 判断是否工作日
     * @param date
     * @return
     */
    public static boolean isWorkday(LocalDate date) {
        Boolean specialDay = isSpecialDay(date);
        if (specialDay != null) {
            return specialDay;
        }
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        // 周六日
        return (dayOfWeek != DayOfWeek.SATURDAY) && (dayOfWeek != DayOfWeek.SUNDAY);
    }

    /**
     * 判断是否节假日
     * @param date
     * @return
     */
    public static boolean isHoliday(LocalDate date) {
        return !isWorkday(date);
    }
}
