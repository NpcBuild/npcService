package com.npc.core.config;

import com.npc.core.properties.IpWhitelistProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author NPC
 * @description IP白名单拦截器
 * @create 2024/8/16 13:23
 */
@Component
public class IpWhitelistInterceptor implements HandlerInterceptor {


    @Autowired
    private IpWhitelistProperties ipWhitelistProperties;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String remoteAddr = request.getRemoteAddr();
        return true;
//        if (ipWhitelistProperties.getAddresses().contains(remoteAddr)) {
//            return true;
//        } else {
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
//            System.out.println("Access Denied");
//            return false;
//        }
    }
}
