package com.npc.common.login.OAuth2.cof;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author NPC
 * @description
 * @create 2023/8/25 22:33
 */
@Data
@Component
@ConfigurationProperties(prefix = "github.client")
public class GithubProperties {
    private String clientId;
    private String clientSecret;
    private String authorizeUrl;
    private String redirectUrl;
    private String accessTokenUrl;
    private String userInfoUrl;
}
