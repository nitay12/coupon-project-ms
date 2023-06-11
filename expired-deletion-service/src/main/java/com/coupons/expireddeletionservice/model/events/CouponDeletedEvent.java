package com.coupons.expireddeletionservice.model.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class CouponDeletedEvent {
        public Long id;
    }
