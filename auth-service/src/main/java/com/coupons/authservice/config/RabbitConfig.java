package com.coupons.authservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue userDeletedQueue() {
        return new Queue("user-deleted", true);
    }

}
