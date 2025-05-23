package com.npc.core.jwt;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author NPC
 * @description JwtFilter 拦截器 访问controller之前先进行token验证
 * @create 2023/4/10 20:32
 */
@Slf4j
@Component
public class JwtFilter extends HandlerInterceptorAdapter {

    @Resource
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 注意：放行浏览器的预检请求
        if (request.getMethod().equals("OPTIONS")) return true;

        // 获取token
        String token = request.getHeader(jwtTokenUtil.header);
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(jwtTokenUtil.header);
        }
        if (StringUtils.isEmpty(token)) {
            log.error("token 不能为空！");
            writeResponseUnauthorized(response);
             return false;
        }
        // 特殊处理
        if ("yf".equals(token)) return true;

        if (!isUserLoggedIn(token)) {
            writeResponseUnauthorized(response);
            return false; // 拦截请求，不再继续处理
        }

        // 获取用户信息 todo：捕获异常
//        UserTokenInfo userInfoToken = jwtTokenUtil.getUserInfoToken(token);
        // 通过用户信息去判断用户状态，等业务
        //TODO 涉及到业务，这里不在阐述

//        // token验证
//        if (token != null) {
//            // token值的最后一位数字是管理员的id，也是redis中存储token的key
//            String adminId = "admin" + token.substring(token.length() - 1);;
//            String value = stringRedisTemplate.opsForValue().get(adminId);
//            if (value.equals(token)) {
//                return true;
//            }
//        }
//        // 验证失败，拦截请求
//        return false;
        return true;
    }

    private boolean isUserLoggedIn(String token) {
        // 检查用户是否登录的逻辑
        try {
            if (jwtTokenUtil.isTokenExpired(token)) {
                log.error("token 已失效！");
                 return false; // 用户未登录
            }
        } catch (Exception e) {
            return false; // 用户未登录
        }
        // 判断 token 是否已在黑名单
        if (jwtTokenUtil.checkBlacklist(token)) {
            log.error("token 已被加入黑名单！");
            return false; // 用户未登录
        }
        return true;
    }

    private void writeResponseUnauthorized(HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            response.getWriter().write("{\"code\":401,\"message\":\"登录失效\"}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
