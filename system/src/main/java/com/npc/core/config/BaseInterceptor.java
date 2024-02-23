//package com.npc.core.config;
//
//import org.springframework.web.servlet.HandlerInterceptor;
////import com.pro.constant.ErrorConstant;
////import com.pro.utils.*;
////import com.pro.vo.user.UserInfoVO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
//*@description 自定义拦截器
//*@author NPC
//*@create 2023/5/19 0:34
//*/
//@Component
//public class BaseInterceptor implements HandlerInterceptor {
//    private static final String USER_AGENT = "user-agent";
//
//    // 后台管理请求接口白名单前缀
//    private final String[] whiteListPrefix = {"/admin/login", "/admin/css", "/admin/js", "/admin/plugins", "/admin/editormd", "/admin/images", "/netDisk/upload"};
//
////    @Autowired
////    private RedisUtil redisUtil;
//
//    /**
//     * 请求方法执行之前
//     * @param request
//     * @param response
//     * @param handler
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // 在进入这个拦截器之前，对跨域提供支持
//        if (responseCors(response, request)) {
//            return false;
//        }
//
//        // 获取用户访问的路由
//        String uri = request.getRequestURI();
//
////        LOGGER.info("UserAgent: {}", request.getHeader(USER_AGENT));
////        LOGGER.info("用户访问地址: {}, 来源地址: {}", uri, IPKit.getIpAddrByRequest(request));
//
//
//        // 获取登录用户同时刷新用户过期时间
////        UserInfoVO user = redisUtil.getLoginUser(request, true);
//        // 请求拦截
////        if (uri.startsWith("/admin") && user == null && verifyUriPrefix(uri)) {
////            this.responseResult(response);
////            return false;
////        }
//
//        return true;
//    }
//
//    /**
//     * 验证 uri 是否在白名单中
//     * @param uri 统一资源标志符/路由
//     * @return boolean
//     */
//    private boolean verifyUriPrefix(String uri) {
//        if (uri == null) return false;
//        for (String prefix : whiteListPrefix) {
//            // 判断 uri 是否以白名单的前缀开头
//            if (uri.startsWith(prefix)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * 用户未登录 使用 response 返回结果
//     * @param response
//     * @throws IOException
//     */
//    private void responseResult(HttpServletResponse response) throws IOException {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json; charset=utf-8");
////        response.getWriter().println(JsonKit.toJSON(Result.fail(401, ErrorConstant.Auth.NOT_LOGIN)));
//    }
//
//    /**
//     * 在进入这个拦截器之前, 对跨域提供支持
//     * @param response
//     * @param request
//     * @return
//     */
//    private boolean responseCors(HttpServletResponse response, HttpServletRequest request) {
//        if (RequestMethod.OPTIONS.name().equals(request.getMethod())) {
//            // response.setHeader("Cache-Control","no-cache");
//            response.setHeader("Access-control-Allow-Origin", "*");
//            response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
//            response.setHeader("Access-Control-Allow-Headers", "*");
//            // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
//            response.setStatus(HttpStatus.OK.value());
//            return true;
//        }
//        return false;
//    }
//}
