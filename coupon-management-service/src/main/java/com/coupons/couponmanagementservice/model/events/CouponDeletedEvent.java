package com.coupons.couponmanagementservice.model.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CouponDeletedEvent {
    public Long id;
}
