package org.example.netty.netty.chat;

import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.example.netty.netty.common.RpcMessageUtil;

import java.util.Scanner;


/**
 * @author chenxuegui
 * @since 2025/4/16
 */
@Slf4j
public class ChatClient_控制台 {

    public static void main(String[] args)  throws Exception{
        ChatClient chatClient = new ChatClient();
        Channel channel = chatClient.connect();

        Scanner scanner = new Scanner(System.in);
        while (channel.isActive()) {
            String line = scanner.nextLine();
            RpcMessageUtil.writeAndFlush(channel,line);
        }
    }
}
