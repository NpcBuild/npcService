package com.npc.common.login.service;

import com.maxmind.geoip2.model.CityResponse;
import com.npc.core.utils.StringUtils;
import com.npc.core.utils.ip.IpUtils;
import com.npc.redis.utils.RedisPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author NPC
 * @description
 * @create 2024/4/8 21:33
 */
@Slf4j
@Service
public class LoginService {

    public Boolean recordIpToRedis(String userIpAddress, String account) {
        Boolean result = true;
        try {
            CityResponse city = IpUtils.ipToLocation(userIpAddress);
            if (null != city) {
                System.out.println(city.toString());
                System.out.println(userIpAddress);
                // 将用户IP地址存入Redis集群 用户id -> 城市
                String cityName = "未查询到";
                if (StringUtils.isNotEmpty(city.getCity().getNames().get("zh-CN"))) {
                    cityName = city.getCity().getNames().get("zh-CN");
                }
                RedisPoolUtil.set(account, cityName);
            }
        } catch (Exception e) {
            log.error("查询IP所在地异常" + e.getMessage());
            result = false;
        } finally {
            return result;
        }
    }
}
