package com.npc.client.Netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.npc.common.modular.user.model.result.UserResult;
import com.npc.common.utils.SpringUtils;
import com.npc.core.jwt.JwtTokenUtil;
import com.npc.core.jwt.UserTokenInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author wow
 * @createTime 2022/11/5 23:32
 * @descripttion
 */
@Slf4j
public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端建立连接，通道开启！" + ctx.channel());

        //添加到channelGroup通道组
        NettyConfig.getChannelGroup().add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端断开连接，通道关闭！");

        //删除出channelGroup 通道组
        NettyConfig.getChannelGroup().remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //首次连接是FullHttpRequest，处理参数
        if (null != msg && msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            Map paramMap=getUrlParams(uri);
            System.out.println("接收到的参数是："+JSON.toJSONString(paramMap));
            //如果url包含参数，需要处理
            if(uri.contains("?")){
                String newUri=uri.substring(0,uri.indexOf("?"));
                System.out.println(newUri);
                request.setUri(newUri);
            }
            String uid = "";
            if (paramMap.containsKey("uuid")) {
                uid = paramMap.get("uuid").toString();
            } else if (paramMap.containsKey("uid")) {
                // 获取用户ID，关联channel
                uid = paramMap.get("uid").toString();
                if (jwtTokenUtil == null) {// 解决接口为null无法注入问题
                    jwtTokenUtil = SpringUtils.getBean(JwtTokenUtil.class);
                }
                uid = jwtTokenUtil.getUserInfoToken(uid).getUserId().toString();
            }
            if (!NettyConfig.getChannelMap().containsKey(uid)) {
                log.info("放入"+uid+"到ChannelMap");
                NettyConfig.getChannelMap().put(uid,ctx.channel());
            }
            // 将用户ID作为自定义属性加入到channel中，方便随时channel中获取用户ID
            AttributeKey<String> key = AttributeKey.valueOf("userId");
            ctx.channel().attr(key).setIfAbsent(uid);
            // 回复消息
            ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器收到消息啦"));
        }else if(msg instanceof TextWebSocketFrame){
            //正常的TEXT消息类型
            TextWebSocketFrame frame=(TextWebSocketFrame)msg;
            System.out.println("客户端收到服务器数据：" +frame.text());
            sendAllMessage(frame.text());
        }
        super.channelRead(ctx, msg);
    }

    /**
     * 一旦连接，第一个被执行
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("有新的客户端链接：[{}]", ctx.channel().id().asLongText());
        // 添加到channelGroup 通道组
        NettyConfig.getChannelGroup().add(ctx.channel());
    }

    /**
     * 读取数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("服务器收到消息：{}", msg.text());

//        // 获取用户ID，关联channel
//        JSONObject jsonObject = JSONObject.parseObject(msg.text());
//        String uid = jsonObject.getString("uid");
//        NettyConfig.getChannelMap().put(uid,ctx.channel());
//
//        // 将用户ID作为自定义属性加入到channel中，方便随时channel中获取用户ID
//        AttributeKey<String> key = AttributeKey.valueOf("userId");
//        ctx.channel().attr(key).setIfAbsent(uid);
//
//        // 回复消息
//        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器收到消息啦"));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("用户下线了：{}", ctx.channel().id().asLongText());
        // 删除通道
        NettyConfig.getChannelGroup().remove(ctx.channel());
        // todo 揭开注释
//        removeUserId(ctx);
    }

    private void sendAllMessage(String message){
        //收到信息后，群发给所有channel
        NettyConfig.getChannelGroup().writeAndFlush( new TextWebSocketFrame(message));
    }

    private static Map getUrlParams(String url){
        Map<String,String> map = new HashMap<>();
        url = url.replace("?",";");
        if (!url.contains(";")){
            return map;
        }

        if (url.split(";").length > 0){
            String[] arr = url.split(";")[1].split("&");
            for (String s : arr){
                String key = s.split("=")[0];
                String value = s.split("=")[1];
                map.put(key,value);
            }
            return  map;
        }else{
            return map;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("异常：{}",cause.getMessage());
        // 删除通道
        NettyConfig.getChannelGroup().remove(ctx.channel());
        removeUserId(ctx);
        ctx.close();
    }

    /**
     * 删除用户与channel的对应关系
     */
    private void removeUserId(ChannelHandlerContext ctx) {
        AttributeKey<String> key = AttributeKey.valueOf("userId");
        String userId = ctx.channel().attr(key).get();
        NettyConfig.getChannelMap().remove(userId);
    }
}
