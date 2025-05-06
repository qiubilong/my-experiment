package org.example.web.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 注意下@RocketMQMessageListener这个注解的其他属性
 **/
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "MyConsumerGroup", topic = "TestTopic",consumeMode= ConsumeMode.CONCURRENTLY,messageModel= MessageModel.CLUSTERING)
//@RocketMQMessageListener(consumerGroup = "stock_consumer_group", topic = "reduce-stock")
public class RocketMqTestConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("Received message : "+ message);
    }
}
