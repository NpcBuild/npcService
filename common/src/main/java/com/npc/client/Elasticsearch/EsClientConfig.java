package com.npc.client.Elasticsearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestClient;

import javax.annotation.PostConstruct;

@Configuration
// fixme 这个类正确吗？
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class EsClientConfig {
    @Autowired
    private Environment env;

    @Value("${spring.data.elasticsearch.client.rest.endpoints}")
    private String restEndPoints;

    @Value("${spring.data.elasticsearch.client.rest.username}")
    private String userName;

    @Value("${spring.data.elasticsearch.client.rest.password}")
    private String passWord;

    //    @PostConstruct在@Bean之前执行
    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

//    @Override
//    @Bean
//    public RestHighLevelClient elasticsearchClient() {
//
//        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo(restEndPoints)
//                .build();
//
//        System.out.println("初始化ES客户端");
//        return RestClients.create(clientConfiguration).rest();
//    }

    @Bean
    public RestHighLevelClient elasticsearchClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        return client;
    }
//    @Bean
//    public ElasticsearchClient elasticsearchClient() {
//        HttpHost[] httpHosts = toHttpHost();
//        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(
//                AuthScope.ANY, new UsernamePasswordCredentials(userName, passWord));
//
//        RestClientBuilder builder = RestClient.builder(httpHosts);
//        builder.setRequestConfigCallback(
//                new RestClientBuilder.RequestConfigCallback() {
//                    @Override
//                    public RequestConfig.Builder customizeRequestConfig(
//                            RequestConfig.Builder requestConfigBuilder) {
//                        return requestConfigBuilder.setSocketTimeout(60000).setConnectTimeout(5000);
//                    }
//                });
//        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//            @Override
//            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
//
//                return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//            }
//        });
////        RestClient restClient = builder.build();
////        ElasticsearchTransport transport = new RestClientTransport(restClient,new JacksonJsonpMapper());
////        return new ElasticsearchClient(transport);
//
//        // 创建 RestClient 对象
//        RestClient restClient = builder.build();
//
//        // 创建 RestClientTransport 对象
//        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
//
//        // 创建 RestHighLevelClient 对象
//        ElasticsearchClient restHighLevelClient = new ElasticsearchClient(transport);
//        return restHighLevelClient;
//    }
//
//    private HttpHost[] toHttpHost() {
//        if (!StringUtils.hasLength(restEndPoints)) {
//            throw new RuntimeException("invalid elasticsearch configuration. elasticsearch.hosts不能为空！");
//        }
//        // 多个IP逗号隔开
//        String[] hostArray = restEndPoints.split(",");
//        HttpHost[] httpHosts = new HttpHost[hostArray.length];
//        HttpHost httpHost;
//        for (int i = 0; i < hostArray.length; i++) {
//            String[] strings = hostArray[i].split(":");
//            httpHost = new HttpHost(strings[0], Integer.parseInt(strings[1]), "http");
//            httpHosts[i] = httpHost;
//        }
//        return httpHosts;
//    }
}
