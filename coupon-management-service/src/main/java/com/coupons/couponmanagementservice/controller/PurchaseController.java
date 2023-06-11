package com.coupons.couponmanagementservice.controller;

import com.coupons.couponmanagementservice.exception.EntityNotFoundException;
import com.coupons.couponmanagementservice.model.Purchase;
import com.coupons.couponmanagementservice.security.JwtWrapper;
import com.coupons.couponmanagementservice.service.JwtService;
import com.coupons.couponmanagementservice.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/purchases")
@CrossOrigin()
public class PurchaseController {
    private final JwtService jwtService;
    private final PurchaseService purchaseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Purchase createPurchase(@RequestBody Purchase purchase, @RequestHeader("Authorization") JwtWrapper authHeader) {
        Long customerId = jwtService.getUserIdFromToken(authHeader.getToken());
        purchase.setCustomerId(customerId);
        return purchaseService.createPurchase(purchase);
    }

    @GetMapping
    public List<Purchase> getAllPurchases() {
        return purchaseService.getAllPurchases();
    }

    @GetMapping("{purchaseId}")
    public Purchase getPurchaseByID(@PathVariable("purchaseId") Long purchaseId) throws EntityNotFoundException {
        return purchaseService.getPurchaseById(purchaseId);
    }

    @PutMapping
    public Purchase updatePurchase(Purchase purchase) throws EntityNotFoundException {
        return purchaseService.updatePurchase(purchase);
    }

    @DeleteMapping
    public void deletePurchase(Long purchaseId) throws EntityNotFoundException {
        purchaseService.deletePurchaseById(purchaseId);
    }
}
