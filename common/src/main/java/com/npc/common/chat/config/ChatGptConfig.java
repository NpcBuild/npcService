package com.npc.common.chat.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * @author NPC
 * @description ChatGPT配置
 * @create 2023/3/26 15:27
 */
@Component
public class ChatGptConfig {
    private final RestTemplateBuilder restTemplateBuilder;

    ChatGptConfig(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    // 创建RestTemplate Bean，并配置字符集
    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.additionalMessageConverters(new StringHttpMessageConverter(Charset.forName("UTF-8"))).build();
    }
}
