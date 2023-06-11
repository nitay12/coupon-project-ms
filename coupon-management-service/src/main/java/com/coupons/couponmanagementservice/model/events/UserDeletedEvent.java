package com.coupons.couponmanagementservice.model.events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserDeletedEvent {
    private Long userId;
    private Set<String> roles;
}
