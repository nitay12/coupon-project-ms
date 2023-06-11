package com.coupons.expireddeletionservice.model.events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CouponCreatedEvent {
    private Long id;
    private LocalDate expirationDate;
}
