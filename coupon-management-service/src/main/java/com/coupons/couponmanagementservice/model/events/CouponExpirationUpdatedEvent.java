package com.coupons.couponmanagementservice.model.events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CouponExpirationUpdatedEvent {
    public Long id;
    public LocalDate expirationDate;
}
