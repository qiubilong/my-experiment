package org.example.netty.netty.chat;

import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.example.netty.netty.common.RpcMessageUtil;


/**
 * @author chenxuegui
 * @since 2025/4/16
 */
@Slf4j
public class ChatClient_拆解包 {

    public static void main(String[] args)  throws Exception{

        ChatClient chatClient = new ChatClient();
        Channel channel = chatClient.connect();

        for (int i = 0; i < 10; i++) {
            RpcMessageUtil.writeAndFlush(channel,"hello world!"+i);//测试拆分包
        }

        channel.closeFuture().sync();
    }
}
