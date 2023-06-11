package com.coupons.couponmanagementservice.model.events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CouponCreatedEvent {
    public Long id;
    public String expirationDate;

    public CouponCreatedEvent(Long id, LocalDate expirationDate){
        this.id = id;
        this.expirationDate = expirationDate.toString();
    }
}
