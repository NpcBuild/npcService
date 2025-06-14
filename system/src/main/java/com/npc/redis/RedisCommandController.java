package com.npc.redis;

import com.npc.redis.utils.RedisPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RedisCommandController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 执行自定义的 Redis 命令字符串
     * @param command 要执行的 Redis 命令字符串，例如 "SET key value"
     * @return 命令执行结果
     */
    @PostMapping("/executeRedisCommandCluster")
    public Object executeRedisCommandCluster(@RequestBody String command) {
        RedisPoolUtil.set("money_point", "2025-02-27 17:00:00,142857");
        System.out.println("command:" + command);
        // 将命令字符串拆分为命令参数数组
        String[] commandArray = command.split(" ");
        // 第一个元素为命令名
        String cmd = commandArray[0];
        // 其余元素为命令参数
        String[] args = Arrays.copyOfRange(commandArray, 1, commandArray.length);

        try {
            // 根据不同的命令执行不同的操作，这里以常见的命令为例
            switch (cmd.toUpperCase()) {
                case "SET":
                    return RedisPoolUtil.set(args[0], args[1]);
                case "GET":
                    return RedisPoolUtil.get(args[0]);
                case "DEL":
                    return RedisPoolUtil.del(Arrays.toString(args));
                default:
                    throw new UnsupportedOperationException("不支持的命令: " + cmd);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行自定义的 Redis 命令字符串
     * @param command 要执行的 Redis 命令字符串，例如 "SET key value"
     * @return 命令执行结果
     */
    @PostMapping("/executeRedisCommand")
    public Object executeRedisCommand(@RequestBody String command) {
        // 将命令字符串拆分为命令参数数组
        String[] commandArray = command.split(" ");
        List<byte[]> args = new ArrayList<>();
        for (String arg : commandArray) {
            args.add(arg.getBytes());
        }

        // 获取 Redis 连接
        RedisConnection connection = stringRedisTemplate.getConnectionFactory().getConnection();
        try {
            // 执行 Redis 命令
            return connection.execute(commandArray[0], args.toArray(new byte[0][]));
        } finally {
            // 关闭连接
            connection.close();
        }
    }
}
