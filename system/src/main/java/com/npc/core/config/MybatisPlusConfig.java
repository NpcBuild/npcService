package com.npc.core.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author NPC
 * @description 分页配置
 * 分页实现：
 * Page类是IPage接口的默认实现类，它的构造函数可以传入当前页码和每页显示的记录数等参数，还包含了一些方便进行武力分页操作的方法
 * 如：setASc(String... ascs)
 * setDesc(String... descs)
 * convert() 转换当前页的结果集
 * optimizeCountSql() 是否启用count sql最优化，默认true
 * optimizeJoin() 是否为join语句count 优化，默认false
 * @create 2024/7/1 9:36
 */
@Configuration
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件，数据库配置根据数据库选择
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
