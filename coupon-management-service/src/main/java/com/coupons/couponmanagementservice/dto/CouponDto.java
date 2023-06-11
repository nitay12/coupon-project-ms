package com.coupons.couponmanagementservice.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record CouponDto(Long id, Long companyId, String code, String title, String description, Double discountPercent,
                        LocalDate startDate, LocalDate expirationDate, Integer amount,
                        String imageURL) implements Serializable {
}
