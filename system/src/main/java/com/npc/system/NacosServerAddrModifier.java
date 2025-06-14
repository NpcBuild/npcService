package com.npc.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.*;

import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author NPC
 * @description 在项目启动时，修改nacos的地址
 * EnvironmentPostProcessor 接口允许你在 Spring 应用上下文创建之前对环境配置进行修改
 * @create 2025/2/5 12:00
 */
public class NacosServerAddrModifier implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            System.out.println("测试机");
            // 遍历网络接口
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                // 遍历IP地址
                Enumeration<java.net.InetAddress> inetAddresses = networkInterface.getInetAddresses();
                System.out.println("inetAddresses");
                while (inetAddresses.hasMoreElements()) {
                    java.net.InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress instanceof Inet4Address && inetAddress.getHostAddress().startsWith("192.168.1.")) {
                        // 获取到符合条件的IP
                        String localIp = inetAddress.getHostAddress();
                        System.out.println("localIp" + localIp);

                        Map<String, Object> source = new HashMap<>();
                        source.put("spring.cloud.nacos.discovery.server-addr", localIp);

                        MapPropertySource propertySource = new MapPropertySource("dynamicNacosServerAddr", source);
                        // 将自定义属性源添加到属性源列表的最前面
                        environment.getPropertySources().addFirst(propertySource);
                        System.out.println("根据环境，实时修改nacos server-addr属性为：" + localIp);

//                        // 获取配置属性源
//                        MutablePropertySources propertySources = environment.getPropertySources();
//                        // 获取yml配置文件对应的属性源
//                        CompositePropertySource compositePropertySource = (CompositePropertySource) propertySources.get("Config resource 'class path resource [application-system.yml]' via location 'optional:classpath:/' (document #0)");
//                        // 遍历属性源中的每个属性源
//                        for(PropertySource<?> source : compositePropertySource.getPropertySources()) {
//                            // 如果是yml文件解析后的属性源
//                            if (source instanceof MapPropertySource) {
//                                MapPropertySource mapSource = (MapPropertySource) source;
//                                // 获取属性源中的属性
//                                Map<String, Object> sourceMap = mapSource.getSource();
//                                // 修改nacos server-addr属性
//                                System.out.println("修改nacos server-addr属性为：" + localIp);
//                                sourceMap.put("spring.cloud.nacos.discovery.server-addr", localIp + ":8848");
//                            }
//                        }
//                        return;
//                        // 修改nacos地址
////                        compositePropertySource.getProperty("spring.cloud.nacos.discovery.server-addr").setValue(localIp + ":8848");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
