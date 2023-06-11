package com.coupons.couponmanagementservice.messaging.producer;

import com.coupons.couponmanagementservice.model.Coupon;
import com.coupons.couponmanagementservice.model.events.CouponCreatedEvent;
import com.coupons.couponmanagementservice.model.events.CouponDeletedEvent;
import com.coupons.couponmanagementservice.model.events.CouponExpirationUpdatedEvent;
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

    public void sendCouponCreatedEvent(Coupon coupon) {
        CouponCreatedEvent event = new CouponCreatedEvent(coupon.getId(), coupon.getExpirationDate());
        sendMessage("coupon-created", event);
    }

    public void sendCouponDeletedEvent(Long couponId) {
        CouponDeletedEvent event = new CouponDeletedEvent(couponId);
        sendMessage("coupon-deleted", event);
    }

    public void sendCouponExpirationUpdatedEvent(Coupon updatedCoupon) {
        CouponExpirationUpdatedEvent event = new CouponExpirationUpdatedEvent(updatedCoupon.getId(), updatedCoupon.getExpirationDate());
        sendMessage("coupon-expiration-updated", event);
    }
    private void sendMessage(String routingKey, Object event){
        try {
            rabbitTemplate.convertAndSend(routingKey, objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
