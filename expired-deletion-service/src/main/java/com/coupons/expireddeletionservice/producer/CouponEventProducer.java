package com.coupons.expireddeletionservice.producer;

import com.coupons.expireddeletionservice.model.Coupon;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponEventProducer {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendCouponExpiredEvent(Coupon coupon) {
        sendMessage("coupon-expired", coupon.getId());
    }
    private void sendMessage(String routingKey, Object event){
        try {
            rabbitTemplate.convertAndSend(routingKey, objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
