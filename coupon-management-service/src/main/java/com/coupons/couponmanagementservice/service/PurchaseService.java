package com.coupons.couponmanagementservice.service;

import com.coupons.couponmanagementservice.exception.EntityNotFoundException;
import com.coupons.couponmanagementservice.model.Purchase;

import java.util.List;

public interface PurchaseService {
    Purchase createPurchase(Purchase purchase);

    Purchase getPurchaseById(Long purchaseId) throws EntityNotFoundException;

    Purchase updatePurchase(Purchase purchase) throws EntityNotFoundException;

    void deletePurchaseById(Long purchaseId) throws EntityNotFoundException;
    void deleteAllCustomerPurchases(Long customerId);

    List<Purchase> getAllPurchases();
}
