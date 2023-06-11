package com.coupons.expireddeletionservice.consumer;

import com.coupons.expireddeletionservice.model.Coupon;
import com.coupons.expireddeletionservice.model.events.CouponCreatedEvent;
import com.coupons.expireddeletionservice.model.events.CouponDeletedEvent;
import com.coupons.expireddeletionservice.model.events.CouponExpirationUpdatedEvent;
import com.coupons.expireddeletionservice.service.CouponService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CouponEventsConsumer {
    private final CouponService couponService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = {"coupon-created"})
    public void consumeCouponCreatedEvent(String message) {
        log.info("Received coupon created event message: " + message);
        try {
            final CouponCreatedEvent event = objectMapper.readValue(message, CouponCreatedEvent.class);
            couponService.createCoupon(new Coupon(event.getId(), event.getExpirationDate()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = {"coupon-deleted"})
    public void consumeCouponDeletedEvent(String message) {
        try {
            log.info("Received coupon deleted event: " + message);
            final CouponDeletedEvent event = objectMapper.readValue(message, CouponDeletedEvent.class);
            couponService.deleteCoupon(event.getId());
            log.info("Coupon deleted: " + event.getId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = {"coupon-expiration-updated"})
    public void consumeCouponExpirationUpdatedEvent(CouponExpirationUpdatedEvent event) {
        // Logic to handle the coupon expiration updated event
        System.out.println("Received coupon expiration updated event: " + event.getId());
    }
}
