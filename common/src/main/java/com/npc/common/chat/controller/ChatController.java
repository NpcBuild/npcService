package com.npc.common.chat.controller;

import com.alibaba.fastjson.JSONObject;
import com.npc.common.chat.entity.UserInput;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import com.npc.core.net.RestTemplateBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @author NPC
 * @description 语音助手
 * @create 2023/3/26 15:07
 */
@Slf4j
@CrossOrigin
@RestController
public class ChatController {
//    @Autowired
//    private RestTemplate restTemplate;
    @Value("${chatgpt.endpoint}")
    private String chatgptEndpoint;
    @Value("${chatgpt.apiKey}")
    private String apiKey;

    @PostMapping("/chat")
    public ServerResponseVO<UserInput> chat(@RequestBody UserInput userInput) {
        String userText = userInput.getText(); // 获取用户输入文本


        // 创建ChatGPT API的请求体
        JSONObject chatgptRequestBody = new JSONObject();
        chatgptRequestBody.put("model", "text-davinci-003");
        chatgptRequestBody.put("prompt", userText);
        chatgptRequestBody.put("max_tokens", 1000);

        // 创建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(apiKey);

        // 创建HttpEntity对象，包含请求体和请求头
        HttpEntity<String> request = new HttpEntity<>(chatgptRequestBody.toString(),headers);

        // 发送POST请求到ChatGPT API，并获取响应
        try {
            log.info("Sending API request: {}", request);
            RestTemplate restTemplate = new RestTemplateBuilder().build();

            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(100000); // 设置连接超时时间为 10 秒
            restTemplate.setRequestFactory(factory);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(chatgptEndpoint, request, String.class);
            log.info("Received API response: {}", responseEntity.getBody());
//            String response = "我是ChatGPT，有什么需要";
            String response = responseEntity.getBody();
            // 将响应结果返回给前端
            UserInput userInput1 = new UserInput();
            userInput1.setResponse(response);
            return ServerResponseVO.success(userInput1);
        } catch (HttpClientErrorException e) {
            log.error("客户端请求异常");
            e.printStackTrace();
        } catch (HttpServerErrorException e) {
            log.error("服务器异常");
            e.printStackTrace();
        } catch (ResourceAccessException e) {
            log.error("网络异常");
            e.printStackTrace();
        }
        return ServerResponseVO.error(ServerResponseEnum.ERROR);
    }
}
