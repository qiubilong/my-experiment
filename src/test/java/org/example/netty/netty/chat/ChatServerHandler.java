package org.example.netty.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.example.netty.netty.common.RpcMessage;
import org.example.netty.netty.common.RpcMessageUtil;
import org.example.netty.netty.common.UserMessage;
import org.example.netty.tuling.netty.codec.ProtostuffUtil;

/**
 * @author chenxuegui
 * @since 2025/4/16
 */
@Slf4j
public class ChatServerHandler extends SimpleChannelInboundHandler<RpcMessage> {


    static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override /* channelRead()ByteBuf消息参数  -->  channelRead0()泛型消息参数    */
    protected void channelRead0(ChannelHandlerContext ctx, RpcMessage rpcMessage) throws Exception {
        Channel channel = ctx.channel();
        UserMessage userMessage = ProtostuffUtil.deserializer(rpcMessage.getContent(), UserMessage.class);
        log.info("客户端["+channel.remoteAddress()+"]发送消息 ："+ userMessage);

        for (Channel channelClient : channelGroup) {
            if(channelClient != channel){
                RpcMessageUtil.writeAndFlush(channelClient,userMessage);
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
            RpcMessageUtil.writeAndFlush(channelClient,"客户端["+channel.remoteAddress()+"]已上线...");
        }
        channelGroup.add(channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("客户端["+channel.remoteAddress()+"]断开连接");
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
