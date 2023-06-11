package com.coupons.couponmanagementservice.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record PurchaseDto(Long id, Long customerId, LocalDateTime purchasedAt) implements Serializable {
}
