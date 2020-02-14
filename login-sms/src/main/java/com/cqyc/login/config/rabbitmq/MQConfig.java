package com.cqyc.login.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {


    public static final String QUEUE = "exchange-rabbit-monitor";

    /**
     * 队列名
     */
    public static final String SEND_MESSAGE_QUEUE = "cqyc-send-message";

    public static final String LIKE_MESSAGE_QUEUE = "cqyc-like-send";

    /**
     * 交换机
     */
    public static final String SEND_MESSAGE_EXCHANGE = "cqyc-exchange";

    public static final String SEND_MESSAGE_ROUTING_KEY1 = "send-message";

    public static final String SEND_MESSAGE_ROUTING_KEY2 = "send-like";

    /**
     * Direct模式 交换机Exchange
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }

}
