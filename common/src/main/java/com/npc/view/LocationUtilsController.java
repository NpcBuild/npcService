package com.npc.view;

import com.maxmind.geoip2.model.CityResponse;
import com.npc.common.mail.server.MailService;
import com.npc.utils.DateUtils;
import com.npc.core.utils.ip.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author NPC
 * @description
 * @create 2023/12/19 21:39
 */
@Slf4j
@RestController
public class LocationUtilsController {
    @Resource
    private MailService mailService;
    @Resource
    private SearchHttpSN searchHttpSN;

    @Async
    @GetMapping("/st")
    public void getGdp(String qw) {
//        String[] split = qw.split(",");
//        String s = split[0];
//        String s1 = split[1];

        // 发送消息提醒做任务
        String to = "1623285565@qq.com";
        String subject = "GPS";
        String templateName = "GPS";

        Map<String, Object> variables = new HashMap<>();
        variables.put("time", DateUtils.getTime());
        variables.put("GPS",qw);
        try {
            mailService.sendEmail(to,subject,templateName,variables);
            log.info("发送成功");
        } catch (MessagingException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Async
    @GetMapping("/vid")
    public void getIp(String i) {
//        String userIpAddress = IpUtils.getIpAddr(request);
        String userIpAddress = i;
        String cityResponseString = "未查询到";
        try {
            CityResponse city = IpUtils.ipToLocation(userIpAddress);
            log.info("解析地址成功");
            // 将CityResponse对象转化为字符串
            cityResponseString = convertCityResponseToString(city);
            log.info("成功：" + cityResponseString);
        } catch (Exception e) {
            log.error("查询IP所在地异常" + e.getMessage());
        }

        String info = "";
        try {
            info= searchHttpSN.getInfo(userIpAddress, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 发送消息提醒做任务
        String to = "1623285565@qq.com";
        String subject = "IP地址";
        String templateName = "IP";

        Map<String, Object> variables = new HashMap<>();
        variables.put("time",DateUtils.getTime());
        variables.put("ip",userIpAddress);
        variables.put("cityResponseString",cityResponseString);
        variables.put("baidu",info);
        try {
            mailService.sendEmail(to,subject,templateName,variables);
            log.info("发送成功");
        } catch (MessagingException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // 将CityResponse对象转化为字符串的方法
    private static String convertCityResponseToString(CityResponse cityResponse) {
        // 使用StringBuilder拼接各个信息
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("城市: ").append(cityResponse.getCity().getNames().get("en")).append("\n");
        stringBuilder.append("洲: ").append(cityResponse.getContinent().getNames().get("en")).append("\n");
        stringBuilder.append("国家: ").append(cityResponse.getCountry().getNames().get("en")).append("\n");
        stringBuilder.append("纬度: ").append(cityResponse.getLocation().getLatitude()).append("\n");
        stringBuilder.append("经度: ").append(cityResponse.getLocation().getLongitude()).append("\n");
        stringBuilder.append("时区: ").append(cityResponse.getLocation().getTimeZone()).append("\n");

        // 还可以根据需要添加其他信息

        return stringBuilder.toString();
    }
}
