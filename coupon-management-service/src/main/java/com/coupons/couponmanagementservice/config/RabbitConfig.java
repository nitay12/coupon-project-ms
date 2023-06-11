package com.coupons.couponmanagementservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue couponCreatedQueue() {
        return new Queue("coupon-created", true);
    }

    @Bean
    public Queue couponDeletedQueue() {
        return new Queue("coupon-deleted", true);
    }

    @Bean
    public Queue couponExpiredQueue() {
        return new Queue("coupon-expired", true);
    }

    @Bean
    public Queue couponUpdatedQueue() {
        return new Queue("coupon-expiration-updated", true);
    }

    @Bean
    public Queue userDeletedQueue() {
        return new Queue("user-deleted", true);
    }

}
