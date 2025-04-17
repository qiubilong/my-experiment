package org.example.netty.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.example.netty.netty.common.UserMessage;
import org.example.netty.tuling.netty.codec.ProtostuffUtil;

/**
 * @author chenxuegui
 * @since 2025/4/16
 */
@Slf4j
public class ChatServerHandler_Proto extends ChannelInboundHandlerAdapter {

    static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();

        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("channelRead byteBuf={}",byteBuf);

        byte[] bytes = new byte[10];
        byteBuf.readBytes(bytes);
      /*  UserMessage userMessage = ProtostuffUtil.deserializer(bytes, UserMessage.class);
        log.info("客户端["+channel.remoteAddress()+"]发送消息 ："+ userMessage);*/

       /* for (Channel channelClient : channelGroup) {
            if(channelClient != channel){
                channelClient.writeAndFlush(copy.copy());
            }
        }*/
    }


    @Override//服务端每次读完一次完整的数据之后，回调该方法，表示数据读取完毕；
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("channelReadComplete 客户端={}",channel.remoteAddress());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("客户端["+channel.remoteAddress()+"]建立连接");

        UserMessage userMessage = new UserMessage(channel.remoteAddress().toString(),"上线");
        byte[] bytes = ProtostuffUtil.serializer(userMessage);
        ByteBuf byteBuf  = Unpooled.copiedBuffer(bytes);
        for (Channel channelClient : channelGroup) {
            channelClient.writeAndFlush(byteBuf.copy());
        }

        channelGroup.add(channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("收到客户端["+channel.remoteAddress()+"]断开连接");
        channelGroup.remove(channel);

        UserMessage userMessage = new UserMessage(channel.remoteAddress().toString(),"下线");
        byte[] bytes = ProtostuffUtil.serializer(userMessage);
        ByteBuf byteBuf  = Unpooled.copiedBuffer(bytes);

        for (Channel channelClient : channelGroup) {
            channelClient.writeAndFlush(byteBuf);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
