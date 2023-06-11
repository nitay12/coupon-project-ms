package com.coupons.expireddeletionservice.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record CouponDto(Long id, LocalDate expirationDate) implements Serializable {
}
