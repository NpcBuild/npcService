package com.npc.core.config;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import org.apache.ibatis.executor.statement.StatementHandler;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyBatisPlusRandomInterceptor implements InnerInterceptor {

    // 正则匹配 ORDER BY（忽略大小写）
    private static final Pattern ORDER_BY_PATTERN = Pattern.compile("(?i)ORDER BY");

    @Override
    public void beforePrepare(StatementHandler statementHandler, Connection connection, Integer transactionTimeout) {
        String sql = statementHandler.getBoundSql().getSql();

        // 获取传入的参数
        Object paramObj = statementHandler.getBoundSql().getParameterObject();

        // **1️⃣ 检查 QueryWrapper 是否携带 DTO**
        if (paramObj instanceof Map) {
            for (Object value : ((Map<?, ?>) paramObj).values()) {
                if (value != null && value instanceof QueryWrapper) {
                    Object entity = ((QueryWrapper<?>) value).getEntity();
                    if (hasRandomTrue(entity)) {
                        sql = appendRandomOrderBy(sql);
                        break;
                    }
                }
            }
        }

        // **2️⃣ 检查 DTO 查询**
        else if (paramObj != null && hasRandomTrue(paramObj)) {
            sql = appendRandomOrderBy(sql);
        }

        // 通过反射修改 SQL
        ReflectUtil.setFieldValue(statementHandler.getBoundSql(), "sql", sql);
    }

    // 检测对象是否包含 `random=true`
    private boolean hasRandomTrue(Object paramObj) {
        if (paramObj == null) {
            System.out.println("paramObj is null");
            return false;
        }

        System.out.println("paramObj class: " + paramObj.getClass().getName());

        try {
            Field field = findField(paramObj.getClass(), "random");
            if (field == null) {
                System.out.println("Field 'random' not found in class hierarchy");
                return false;
            }
            field.setAccessible(true);
            Object value = field.get(paramObj);
            return Boolean.TRUE.equals(value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Field findField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass(); // 递归查找父类
            }
        }
        return null;
    }


    // **处理 SQL 追加 `ORDER BY RAND()`**
    private String appendRandomOrderBy(String sql) {
        Matcher matcher = ORDER_BY_PATTERN.matcher(sql);
        if (matcher.find()) {
            // 如果已有 ORDER BY，则在其后追加 ", RAND()"
            return sql.replaceFirst("(?i)ORDER BY", "ORDER BY RAND(),");
        } else {
            // 如果没有 ORDER BY，直接追加
            return sql + " ORDER BY RAND()";
        }
    }
}
