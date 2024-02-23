package com.npc.client.Netty.service.impl;

import com.alibaba.fastjson.JSON;
import com.npc.client.Netty.NettyConfig;
import com.npc.client.Netty.service.PushMsgService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author wow
 * @createTime 2022/11/19 23:06
 * @descripttion 使用netty推送消息
 */
@Slf4j
@Service
public class PushMsgServiceImpl implements PushMsgService {

    /**
     * 推送给指定用户
     *
     * @param userId
     * @param msg
     */
    @Override
    public void pushMsgToOne(String userId, Object msg) {
        log.info("取出"+userId+"到ChannelMap");
        Channel channel = NettyConfig.getChannel(userId);
        if (Objects.isNull(channel)) {
            return;
//            throw new RuntimeException("未连接socket服务器");
        }
        String send;
        if (msg instanceof String) {
            send = (String) msg;
        } else {
            send = JSON.toJSONString(msg);
        }
        channel.writeAndFlush(new TextWebSocketFrame(send));
        log.info("消息发送成功");
    }

    /**
     * 推送给所有用户
     *
     * @param msg
     */
    @Override
    public void pushMsgToAll(String msg) {
        NettyConfig.getChannelGroup().writeAndFlush(new TextWebSocketFrame(msg));
    }
}
