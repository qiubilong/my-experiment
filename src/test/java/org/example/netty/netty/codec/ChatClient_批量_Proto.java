package org.example.netty.netty.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.example.netty.netty.common.UserMessage;
import org.example.netty.tuling.netty.codec.ProtostuffUtil;

/**
 * @author chenxuegui
 * @since 2025/4/16
 */
@Slf4j
public class ChatClient_批量_Proto {

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

                    pipeline.addLast("clientHandler_Proto",new ChatClientHandler_Proto());
                }
            });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000).sync();
            Channel channel = channelFuture.channel();

            for (int i = 0; i < 50; i++) {
                UserMessage userMessage = new UserMessage(channel.localAddress().toString(),"hello world"+i);
                byte[] bytes = ProtostuffUtil.serializer(userMessage);
                ByteBuf byteBuf  = Unpooled.copiedBuffer(bytes);
                channel.writeAndFlush(byteBuf);
            }

            Thread.sleep(10000);
            UserMessage userMessage = new UserMessage(channel.localAddress().toString(),"hello world1111");
            byte[] bytes = ProtostuffUtil.serializer(userMessage);
            ByteBuf byteBuf  = Unpooled.copiedBuffer(bytes);
            channel.writeAndFlush(byteBuf);
            channelFuture.channel().closeFuture().sync();
        }finally {
            work.shutdownGracefully();
        }
    }
}
