package com.coupons.expireddeletionservice.service;


import com.coupons.expireddeletionservice.model.Coupon;
import com.coupons.expireddeletionservice.producer.CouponEventProducer;
import com.coupons.expireddeletionservice.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponEventProducer couponEventProducer;

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Set<Coupon> getExpiredCoupons() {
        return couponRepository.findByExpirationDateBefore(LocalDate.now());
    }

    public void deleteExpiredCoupons() {
        couponRepository.deleteByExpirationDateBefore(LocalDate.now());
    }

    @Scheduled(cron = "0 */2 * * * *")
    public void dailyTask() {
        log.info("started daily task");
        final Set<Coupon> expiredCoupons = getExpiredCoupons();
        for (Coupon c :
                expiredCoupons) {
            log.info("Coupon: " + c.getId() + " expired");
            couponEventProducer.sendCouponExpiredEvent(c);
        }
    }

    public void deleteCoupon(Long couponId) {
        if(couponRepository.existsById(couponId)){
        couponRepository.deleteById(couponId);
        }
    }
}
