package com.npc.core.utils;

import com.npc.core.utils.uuid.SnowflakeIdGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author NPC
 * @description UUID生成器
 * @create 2024/5/7 19:26
 */
public class UUIDUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final int RANDOM_NUM_BOUND = 10000; // 定义随机数范围

    public static long get() {
//        return generateUUID();
        return new SnowflakeIdGenerator(1, 1).nextId();
    }

    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        return uuid.toString().replace("-","");
    }
    public static String generateOrderNum() {
        String prefix = "YF";
        String timestamp = dateFormat.format(new Date());
        int randomNumber = ThreadLocalRandom.current().nextInt(RANDOM_NUM_BOUND);
        return prefix + timestamp + String.format("%04d", randomNumber);
    }

    public static void main(String[] args) {
        generateUUID();
        System.out.println(generateOrderNum());
        System.out.println(new SnowflakeIdGenerator(1,2).nextId());
    }
}
