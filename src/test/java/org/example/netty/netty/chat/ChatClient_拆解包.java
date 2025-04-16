package org.example.netty.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;


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

                    pipeline.addLast("encoder",new StringEncoder());
                    pipeline.addLast("decoder",new StringDecoder());
                    pipeline.addLast("clientHandler",new ChatClientHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000).sync();
            Channel channel = channelFuture.channel();

            for (int i = 0; i < 50; i++) {
                channel.writeAndFlush("hello world!"); /* TCP是字节流，需要程序自定义分隔，还原发送报文 */
            }

        }finally {
            work.shutdownGracefully();
        }
    }
}
