package com.npc.common.login.OAuth2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.npc.common.login.OAuth2.cof.GithubProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * @author NPC
 * @description
 * @create 2023/8/25 22:36
 */
@Controller
public class GithubLoginController {
    @Autowired
    GithubProperties githubProperties;


    /**
     * 登录接口，重定向至github
     *
     * @return 跳转url
     */
    @GetMapping("/authorize")
    public String authorize() {
        String url =githubProperties.getAuthorizeUrl() +
                "?client_id=" + githubProperties.getClientId() +
                "&redirect_uri=" + githubProperties.getRedirectUrl();
        return "redirect:" + url;
    }

    /**
     * 回调接口，用户同意授权后，GitHub会将授权码传递给此接口
     * @param code GitHub重定向时附加的授权码，只能用一次
     * @return
     */
    @GetMapping("/oauth/redirect")
    @ResponseBody
    public String redirect(@RequestParam("code") String code) throws JsonProcessingException {
        System.out.println("code:"+code);
        // 使用code获取token
        String accessToken = this.getAccessToken(code);
        // 使用token获取userInfo
        String userInfo = this.getUserInfo(accessToken);
        return userInfo;
    }


    /**
     * 使用授权码获取token
     * @param code
     * @return
     */
    private String getAccessToken(String code) throws JsonProcessingException {
        String url = githubProperties.getAccessTokenUrl() +
                "?client_id=" + githubProperties.getClientId() +
                "&client_secret=" + githubProperties.getClientSecret() +
                "&code=" + code +
                "&grant_type=authorization_code";
        // 构建请求头
        HttpHeaders requestHeaders = new HttpHeaders();
        // 指定响应返回json格式
        requestHeaders.add("accept", "application/json");
        // 构建请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        // post 请求方式
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        String responseStr = response.getBody();
        // 解析响应json字符串
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseStr);
        String accessToken = jsonNode.get("access_token").asText();
        System.out.println("accessToken:"+accessToken);
        return accessToken;
    }

    /**
     *
     * @param accessToken 使用token获取userInfo
     * @return
     */
    private String getUserInfo(String accessToken) {
        String url = githubProperties.getUserInfoUrl();
        // 构建请求头
        HttpHeaders requestHeaders = new HttpHeaders();
        // 指定响应返回json格式
        requestHeaders.add("accept", "application/json");
        // AccessToken放在请求头中
        requestHeaders.add("Authorization", "token " + accessToken);
        // 构建请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        // get请求方式
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String userInfo = response.getBody();
        System.out.println("userInfo:"+userInfo);
        return userInfo;
    }
//    code:22f7d61a30542ea65dcf
//    accessToken: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
//    userInfo:{"login":"NpcBuild","id":62881534,"node_id":"MDQ6VXNlcjYyODgxNTM0","avatar_url":"https://avatars.githubusercontent.com/u/62881534?v=4","gravatar_id":"","url":"https://api.github.com/users/NpcBuild","html_url":"https://github.com/NpcBuild","followers_url":"https://api.github.com/users/NpcBuild/followers","following_url":"https://api.github.com/users/NpcBuild/following{/other_user}","gists_url":"https://api.github.com/users/NpcBuild/gists{/gist_id}","starred_url":"https://api.github.com/users/NpcBuild/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/NpcBuild/subscriptions","organizations_url":"https://api.github.com/users/NpcBuild/orgs","repos_url":"https://api.github.com/users/NpcBuild/repos","events_url":"https://api.github.com/users/NpcBuild/events{/privacy}","received_events_url":"https://api.github.com/users/NpcBuild/received_events","type":"User","site_admin":false,"name":null,"company":null,"blog":"","location":null,"email":null,"hireable":null,"bio":null,"twitter_username":null,"public_repos":16,"public_gists":0,"followers":0,"following":0,"created_at":"2020-03-30T10:21:12Z","updated_at":"2023-08-25T14:17:20Z"}
}
