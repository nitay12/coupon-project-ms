package com.coupons.couponmanagementservice.controller;

import com.coupons.couponmanagementservice.exception.ApplicationException;
import com.coupons.couponmanagementservice.exception.EntityNotFoundException;
import com.coupons.couponmanagementservice.exception.ForbiddenException;
import com.coupons.couponmanagementservice.model.Coupon;
import com.coupons.couponmanagementservice.security.JwtWrapper;
import com.coupons.couponmanagementservice.service.CouponService;
import com.coupons.couponmanagementservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
@CrossOrigin()
public class CouponController {
    private final CouponService couponService;
    private final JwtService jwtService;

    @GetMapping("/coupons")
    public List<Coupon> getAllCoupons(@RequestParam(required = false) boolean valid, @RequestParam(required = false) String categoryTitle) throws EntityNotFoundException {
        if (valid) return couponService.getValidCoupons();
        else if (Objects.nonNull(categoryTitle)) return couponService.getAllCoupons(categoryTitle);
        else return couponService.getAllCoupons();
    }


    @GetMapping("/companies/{companyId}/coupons")
    public List<Coupon> getAllCoupons(@PathVariable("companyId") Long companyId, @RequestParam(required = false) String categoryTitle) {
        if (Objects.nonNull(categoryTitle)) {
            return couponService.getAllCompanyCoupons(companyId, categoryTitle);
        }
        return couponService.getAllCompanyCoupons(companyId);
    }

    @GetMapping("/coupons/{couponId}")
    public Coupon getCoupon(@PathVariable("couponId") Long couponId) throws ForbiddenException, EntityNotFoundException {
        return couponService.getCouponById(couponId);
    }

    @DeleteMapping("/coupons/{couponId}")
    public void deleteCoupon(@PathVariable("couponId") Long couponId, @RequestHeader("Authorization") JwtWrapper authHeader) throws ForbiddenException, EntityNotFoundException {
        Long companyId = jwtService.getUserIdFromToken(authHeader.getToken());
        couponService.deleteCoupon(couponId, companyId);
    }

    @PostMapping("/coupons")
    @ResponseStatus(HttpStatus.CREATED)
    public Coupon createCoupon(@RequestBody Coupon coupon, @RequestHeader("Authorization") JwtWrapper authHeader) throws ApplicationException {
        Long companyId = jwtService.getUserIdFromToken(authHeader.getToken());
        return couponService.createCoupon(coupon, companyId);
    }

    @PutMapping("/coupons")
    public Coupon updateCoupon(@RequestBody Coupon coupon, @RequestHeader("Authorization") JwtWrapper authHeader) throws ApplicationException {
        Long companyId = jwtService.getUserIdFromToken(authHeader.getToken());
        return couponService.updateCoupon(coupon, companyId);
    }

    @GetMapping("/coupons/valid/{couponId}")
    boolean isCouponValid(@PathVariable("couponId") Long couponId) throws EntityNotFoundException {
        return couponService.isCouponValid(couponId);
    }

    @GetMapping("/coupons/expired/{couponId}")
    boolean isCouponExpired(@PathVariable("couponId") Long couponId) throws EntityNotFoundException {
        return couponService.isCouponExpired(couponId);
    }
}
