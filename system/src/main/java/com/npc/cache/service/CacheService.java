package com.npc.cache.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
/**
 * @program: npcService
 * @description 统一缓存接口
 * @author: feiyang
 * @create: 2025/10/26 13:32
 **/
@Service
public interface CacheService {

    /**
     * 设置缓存
     */
    void set(String key, Object value);

    /**
     * 设置带过期时间的缓存
     */
    void set(String key, Object value, long timeout, TimeUnit unit);

    /**
     * 获取缓存
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 删除缓存
     */
    void delete(String key);

    /**
     * 判断缓存是否存在
     */
    boolean hasKey(String key);

    /**
     * 获取缓存类型
     */
    String getType();
}