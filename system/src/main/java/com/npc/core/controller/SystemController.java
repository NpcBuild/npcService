package com.npc.core.controller;

import com.npc.core.ServerResponseVO;
import com.npc.core.net.query.Workday;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
        res.put("workday", Workday.isWorkday(localDate));
        res.put("specialDay", Workday.isSpecialDay(localDate));
        return ServerResponseVO.success(res);
    }
}
