package com.npc.core.config;

import com.npc.core.jwt.JwtFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * @author NPC
 * @description
 * @create 2023/3/10 20:56
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private JwtFilter jwtFilter;
    @Resource
    private IpWhitelistInterceptor ipWhitelistInterceptor;

    /**
     * 不需要拦截地址
     */
    public static final String[] EXCLUDE_URLS = {
            "/login",
            "/login/**",
            "/qrCode",
            "/share",
            "/refreshToken/**",
            "/netDisk/test",
            "/video/**",
            "/todoView"
    };

    /**
     * 配置token拦截器生效
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipWhitelistInterceptor).addPathPatterns("/**");
        registry.addInterceptor(jwtFilter).addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_URLS);
    }

    /**
     * 配置全局跨域访问
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.print("**************************************************WebMvcConfig已被加载**************************************************");
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许证书（cookies）
                .allowCredentials(true)
                // 设置允许请求方式
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
                // 设置允许的请求头
                .allowedHeaders("*")
                // 预请求的结果能被缓存多久
                .maxAge(3600);
    }
}
