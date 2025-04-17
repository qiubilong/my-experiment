package org.example.netty.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.example.netty.netty.codec.RpcMessageDecoder;
import org.example.netty.netty.codec.RpcMessageEncoder;
import org.example.netty.netty.common.HeartbeatMessage;
import org.example.netty.netty.common.RpcMessageUtil;

import java.util.concurrent.TimeUnit;


/**
 * @author chenxuegui
 * @since 2025/4/16
 */
@Slf4j
public class ChatClient {

    Bootstrap bootstrap;
    NioEventLoopGroup work;
    Channel channel;

    public ChatClient() {
        work = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(work);

        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        bootstrap.option(ChannelOption.TCP_NODELAY,true);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {

                ChannelPipeline pipeline = ch.pipeline();

                //outBound - 对象转字节流
                pipeline.addLast("RpcMessageEncoder", new RpcMessageEncoder());

                // inBound - TCP字节流解码
                pipeline.addLast("RpcMessageDecoder", new RpcMessageDecoder());
                pipeline.addLast("ChatClientHandler", new ChatClientHandler());
            }
        });
    }

    public Channel connect() throws Exception {
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();

                //outBound - 对象转字节流
                pipeline.addLast("RpcMessageEncoder", new RpcMessageEncoder());

                // inBound - TCP字节流解码
                pipeline.addLast("RpcMessageDecoder", new RpcMessageDecoder());
                pipeline.addLast("ChatClientHandler", new ChatClientHandler());
            }
        });
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    log.info("连接服务器成功");
                    doKeepAlive();
                }else {
                    log.info("连接服务器失败");
                }
            }
        });
        channelFuture.sync();
        channel = channelFuture.channel();
        return channel;
    }

    public void doKeepAlive(){ //发送保活心跳
        work.schedule(() -> {
            if(channel!=null && channel.isActive()){
                RpcMessageUtil.writeAndFlushInner(channel, HeartbeatMessage.PING);
                doKeepAlive();
            }

        },30, TimeUnit.SECONDS);

    }

    public void close() throws InterruptedException {
        channel.close().sync();
        work.shutdownGracefully();
    }

    public static void main(String[] args)  throws Exception{

        NioEventLoopGroup work = new NioEventLoopGroup(1);

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(work);

            bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
            bootstrap.option(ChannelOption.TCP_NODELAY,true);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {

                    ChannelPipeline pipeline = ch.pipeline();

                    //outBound - 对象转字节流
                    pipeline.addLast("RpcMessageEncoder",new RpcMessageEncoder());

                    // inBound - TCP字节流解码
                    pipeline.addLast("RpcMessageDecoder",new RpcMessageDecoder());
                    pipeline.addLast("ChatClientHandler",new ChatClientHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000).sync();
            Channel channel = channelFuture.channel();

            for (int i = 0; i < 10; i++) {
                RpcMessageUtil.writeAndFlush(channel,"hello world!"+i);//测试拆分包
            }

            channel.closeFuture().sync();
        }finally {
            work.shutdownGracefully();
        }
    }
}
