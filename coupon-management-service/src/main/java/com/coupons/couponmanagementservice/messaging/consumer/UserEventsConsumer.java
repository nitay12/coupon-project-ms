package com.coupons.couponmanagementservice.messaging.consumer;

import com.coupons.couponmanagementservice.model.events.UserDeletedEvent;
import com.coupons.couponmanagementservice.service.CouponService;
import com.coupons.couponmanagementservice.service.PurchaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class UserEventsConsumer {
    private final ObjectMapper objectMapper;
    private final CouponService couponService;
    private PurchaseService purchaseService;

    @Transactional
    @RabbitListener(queues = {"user-deleted"})
    public void consumeUserDeletedEvent(String message) {
        try {
            final UserDeletedEvent event = objectMapper.readValue(message, UserDeletedEvent.class);
            for (String role :
                    event.getRoles()) {
                switch (role) {
                    case "ROLE_ADMIN":
                        break;
                    case "ROLE_COMPANY":
                        couponService.deleteAllCompanyCoupons(event.getUserId());
                        break;
                    case "ROLE_CUSTOMER":
                        purchaseService.deleteAllCustomerPurchases(event.getUserId());
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
