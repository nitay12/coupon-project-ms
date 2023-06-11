package com.coupons.couponmanagementservice.messaging.consumer;

import com.coupons.couponmanagementservice.service.CouponService;
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

    @RabbitListener(queues = {"coupon-expired"})
    public void consumeCouponExpiredEvent(String message) {
        log.info("Received coupon expired event message: " + message);
            Long couponId = Long.parseLong(message);
            couponService.deleteCoupon(couponId);
            log.info("Deleted coupon: " + couponId);
    }
}
