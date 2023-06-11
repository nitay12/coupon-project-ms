package com.coupons.couponmanagementservice.repository;

import com.coupons.couponmanagementservice.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    long deleteByCustomerId(Long customerId);
}