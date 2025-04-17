package org.example.netty.netty.chat;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.example.netty.netty.common.RpcMessage;
import org.example.netty.netty.common.UserMessage;
import org.example.netty.tuling.netty.codec.ProtostuffUtil;

/**
 * @author chenxuegui
 * @since 2025/4/16
 */
@Slf4j
public class ChatClientHandler extends SimpleChannelInboundHandler<RpcMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMessage rpcMessage) throws Exception {
        Channel channel = ctx.channel();
        UserMessage userMessage = ProtostuffUtil.deserializer(rpcMessage.getContent(), UserMessage.class);
        log.info("收到消息 ："+ userMessage);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("客户端["+channel.localAddress()+"]建立连接成功");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("客户端["+channel.localAddress()+"]断开连接");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}
