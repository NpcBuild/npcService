package com.npc.client.Netty.server;

import com.npc.client.Netty.MyWebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author wow
 * @createTime 2022/11/5 23:29
 * @descripttion Netty服务器配置
 */

public class NettyServer {
    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();  // 负责与客户端之间的读写操作
        EventLoopGroup group = new NioEventLoopGroup();  // 辅助客户端的tcp连接请求

        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.option(ChannelOption.SO_BACKLOG, 1024);
            sb.group(group, bossGroup) // 绑定线程池
                    .channel(NioServerSocketChannel.class) // 指定使用NIO类型的channel
                    .localAddress(this.port)// 绑定监听端口
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 绑定客户端连接时候触发操作
                        /**
                         * webSocket协议名
                         */
                        static final String WEBSOCKET_PROTOCOL = "WebSocket";

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("收到新连接");

                            //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
                            ch.pipeline().addLast(new HttpServerCodec());
                            ch.pipeline().addLast(new ObjectEncoder());

                            //以块的方式来写的处理器
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            ch.pipeline().addLast(new HttpObjectAggregator(8192));
                            // 自定义的handler，处理业务逻辑
                            ch.pipeline().addLast(new MyWebSocketHandler());
                            ch.pipeline().addLast(new WebSocketServerProtocolHandler("/socket", null, true, 65536 * 10));
                        }
                    });

            ChannelFuture cf = sb.bind().sync(); // 服务器异步创建绑定，通过调用sync同步方法阻塞直到绑定成功
            System.out.println(NettyServer.class + " 启动正在监听：" + cf.channel().localAddress());
            cf.channel().closeFuture().sync(); // 对关闭服务器通道进行监听
        } finally {
            group.shutdownGracefully().sync(); // 释放线程池资源
            bossGroup.shutdownGracefully().sync();
        }
    }
}
