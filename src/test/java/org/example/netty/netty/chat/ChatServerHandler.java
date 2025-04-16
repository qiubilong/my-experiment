package org.example.netty.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxuegui
 * @since 2025/4/16
 */
@Slf4j
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        log.info("客户端["+channel.remoteAddress()+"]发送消息 ："+ msg);

        for (Channel channelClient : channelGroup) {
            if(channelClient != channel){
                channelClient.writeAndFlush("客户端["+channel.remoteAddress()+"]发送消息:" + msg);
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("客户端["+channel.remoteAddress()+"]建立连接");
        for (Channel channelClient : channelGroup) {
            channelClient.writeAndFlush("客户端["+channel.remoteAddress()+"]已上线...");
        }

        channelGroup.add(channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("收到客户端["+channel.remoteAddress()+"]断开连接");
        channelGroup.remove(channel);

        for (Channel channelClient : channelGroup) {
            channelClient.writeAndFlush("客户端["+channel.remoteAddress()+"]已下线");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
