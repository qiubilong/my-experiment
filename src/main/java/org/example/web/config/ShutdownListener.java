package org.example.web.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class ShutdownListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("Spring 上下文关闭事件已触发！....event="+event);

        try {
            Thread.sleep(5000); // 添加延迟
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}