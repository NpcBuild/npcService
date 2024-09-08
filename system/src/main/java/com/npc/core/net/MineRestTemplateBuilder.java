package com.npc.core.net;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/**
 * @author NPC
 * @description RestTemplate的构造器，以及绕过https的证书验证
 * @create 2023/8/7 13:28
 */
@Component
public class MineRestTemplateBuilder {
    private int connectTimeout = 50000;
    private int readTimeout = 50000;
    private boolean enableSslCheck = false;

    public MineRestTemplateBuilder connectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }
    public MineRestTemplateBuilder readTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }
    public MineRestTemplateBuilder enableSslCheck(boolean enableSslCheck) {
        this.enableSslCheck = enableSslCheck;
        return this;
    }
    public static MineRestTemplateBuilder builder() {
        return new MineRestTemplateBuilder();
    }

    public RestTemplate build() {
        final RestTemplate restTemplate = new RestTemplate();

        //sslIgnore
        SimpleClientHttpRequestFactory requestFactory;
        if (!this.enableSslCheck) {
            requestFactory = getUnsafeClientHttpRequestFactory();
        } else {
            requestFactory = new SimpleClientHttpRequestFactory();
        }

        // fix Could not extract response: no suitable HttpMessageConverter found for response type
        // 手动添加text/plan,text/html格式
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(
                MediaType.TEXT_HTML,
                MediaType.TEXT_PLAIN));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

        // timeout
        requestFactory.setConnectTimeout(this.connectTimeout);
        requestFactory.setReadTimeout(this.readTimeout);

        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

    private SimpleClientHttpRequestFactory getUnsafeClientHttpRequestFactory() {
        TrustManager[] byPassTrustManagers = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        final SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, byPassTrustManagers, new SecureRandom());
            sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }

        return new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection,
                                             @NotNull String httpMethod) throws IOException {
                super.prepareConnection(connection, httpMethod);
                if (connection instanceof HttpURLConnection) {
                    ((HttpsURLConnection) connection).setSSLSocketFactory(
                            sslContext.getSocketFactory()
                    );
                }
            }
        };
    }
}
