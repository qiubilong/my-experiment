package org.example.netty.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenxuegui
 * @since 2025/4/16
 */
@Slf4j
public class ChatServer_Proto {

    public static void main(String[] args) throws Exception {


        EventLoopGroup boss = new NioEventLoopGroup(1);/* 处理客户端连接accept */
        EventLoopGroup work = new NioEventLoopGroup(4);/* 处理客户端读写 */   //默认核心数2倍

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss,work);//Reactor主从模式

            serverBootstrap.channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_BACKLOG,1024);

            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() { /* 客户端建立连接后，初始化数据处理管道回调 */
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();


                    pipeline.addLast("serverHandler_Proto",new ChatServerHandler_Proto());
                }
            });

            ChannelFuture channelFuture = serverBootstrap.bind(9000).sync();
            log.info("ChatServer 启动成功...");
            channelFuture.channel().closeFuture().sync();
            log.info("ChatServer 关闭...");
        }finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }

    }
}
