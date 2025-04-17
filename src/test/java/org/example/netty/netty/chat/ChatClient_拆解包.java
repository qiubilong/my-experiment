package org.example.netty.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.example.netty.netty.codec.RpcMessageDecoder;
import org.example.netty.netty.codec.RpcMessageEncoder;
import org.example.netty.netty.common.RpcMessageUtil;


/**
 * @author chenxuegui
 * @since 2025/4/16
 */
@Slf4j
public class ChatClient_拆解包 {

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
                RpcMessageUtil.writeAndFlush(channel,"hello world!");//测试拆分包
            }

            channel.closeFuture().sync();
        }finally {
            work.shutdownGracefully();
        }
    }
}
