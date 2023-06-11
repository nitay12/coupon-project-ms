package com.coupons.couponmanagementservice.service;

import com.coupons.couponmanagementservice.exception.EntityNotFoundException;
import com.coupons.couponmanagementservice.model.Coupon;
import com.coupons.couponmanagementservice.model.Purchase;
import com.coupons.couponmanagementservice.repository.CouponRepository;
import com.coupons.couponmanagementservice.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final CouponRepository couponRepository;

    @Transactional
    @Override
    public Purchase createPurchase(Purchase purchase) {
        final Optional<Coupon> coupon = couponRepository.findById(purchase.getCoupon().getId());
        coupon.get().setAmount(coupon.get().getAmount() - 1);
        couponRepository.save(coupon.get());
        return purchaseRepository.saveAndFlush(purchase);
    }

    @Override
    public Purchase getPurchaseById(Long purchaseId) throws EntityNotFoundException {
        return purchaseRepository.findById(purchaseId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Purchase updatePurchase(Purchase purchase) throws EntityNotFoundException {
        if (!purchaseRepository.existsById(purchase.getId())) {
            throw new EntityNotFoundException();
        }
        return purchaseRepository.save(purchase);
    }

    @Override
    public void deletePurchaseById(Long purchaseId) throws EntityNotFoundException {
        if (!purchaseRepository.existsById(purchaseId)) {
            throw new EntityNotFoundException();
        }
        purchaseRepository.deleteById(purchaseId);
    }

    @Override
    public void deleteAllCustomerPurchases(Long customerId) {
        purchaseRepository.deleteByCustomerId(customerId);
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }
}
