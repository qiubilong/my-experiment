package org.example.netty.netty.common;

import io.netty.channel.Channel;
import org.example.netty.tuling.netty.codec.ProtostuffUtil;

/**
 * @author chenxuegui
 * @since 2025/4/17
 */
public class RpcMessageUtil {

    public static void writeAndFlush(Channel channel,String msg){
        UserMessage userMessage = new UserMessage(channel.localAddress().toString(),msg);
        writeAndFlush(channel,userMessage);
    }

    public static void writeAndFlush(Channel channel,UserMessage userMessage){
        RpcMessage rpcMessage = new RpcMessage();
        byte[] bytes = ProtostuffUtil.serializer(userMessage);
        rpcMessage.setLen(bytes.length + 4);
        rpcMessage.setType(0);
        rpcMessage.setContent(bytes);

        channel.writeAndFlush(rpcMessage);
    }
}
