package com.npc.guava;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.npc.common.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author NPC
 * @description
 * @create 2023/8/26 22:47
 */
@Slf4j
public class GuavaLocalCache {
    public static LoadingCache<String, List<String>> weiboCache = CacheBuilder.newBuilder()
            //设置缓存初始大小，应该合理设置，后续会扩容
            .initialCapacity(10)
            //最大值
            .maximumSize(100)
            //并发数设置
            .concurrencyLevel(5)
            //缓存过期时间，写入后10分钟过期
            .expireAfterWrite(600, TimeUnit.SECONDS)
            // 此缓存对象经过多少秒没有被访问则过期。
            .expireAfterAccess(10,TimeUnit.SECONDS)
            //统计缓存命中率
            .recordStats()
            .build(new WeiboCacheLoader());

    public static class WeiboCacheLoader extends CacheLoader<String, List<String>> {
        @Override
        public List<String> load(String key) {
            log.info("加载weibo screeName开始");
//            QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("parent_dir_id", id);
//            String sql = "select column_url from gtcom_media where type_code = 'weibo' limit 10";
//            JdbcTemplate jdbcTemplate = SpringUtils.getApplicationContext().getBean("appJdbcTemplate",JdbcTemplate.class);
//            List<String> list = jdbcTemplate.queryForList(sql, String.class);
//            log.info("select column_url from gtcom_media where type_code = 'weibo' ");
//            log.info("list:{} ",list.size());
//            log.info("加载数据结束");
//            return list;
            return new ArrayList<>();
        }
    }
}
