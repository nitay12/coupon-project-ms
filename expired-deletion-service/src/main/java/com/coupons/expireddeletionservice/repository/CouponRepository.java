package com.coupons.expireddeletionservice.repository;

import com.coupons.expireddeletionservice.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Set<Coupon> findByExpirationDateBefore(LocalDate expirationDate);

    @Transactional
    @Modifying
    @Query("delete from Coupon c where c.expirationDate < ?1")
    void deleteByExpirationDateBefore(LocalDate expirationDate);

}