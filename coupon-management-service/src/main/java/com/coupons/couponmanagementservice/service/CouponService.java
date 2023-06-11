package com.coupons.couponmanagementservice.service;

import com.coupons.couponmanagementservice.exception.EntityNotFoundException;
import com.coupons.couponmanagementservice.exception.ForbiddenException;
import com.coupons.couponmanagementservice.exception.TitleExistException;
import com.coupons.couponmanagementservice.model.Coupon;

import java.util.List;

public interface CouponService {

    Coupon createCoupon(Coupon coupon, Long companyId) throws TitleExistException;

    Coupon updateCoupon(Coupon coupon, Long companyId) throws EntityNotFoundException, ForbiddenException, TitleExistException;

    void deleteCoupon(Long couponId, Long companyId) throws EntityNotFoundException, ForbiddenException;

    void deleteCoupon(Long couponId);

    void deleteAllCompanyCoupons(Long companyId);

    Coupon getCouponById(Long couponId) throws EntityNotFoundException;

    List<Coupon> getAllCoupons();

    List<Coupon> getAllCoupons(String categoryTitle) throws EntityNotFoundException;

    List<Coupon> getAllCompanyCoupons(Long companyId);

    List<Coupon> getAllCompanyCoupons(Long companyId, String categoryTitle);

    List<Coupon> getValidCoupons();

    boolean isCouponValid(Long couponId) throws EntityNotFoundException;

    boolean isCouponExpired(Long couponId) throws EntityNotFoundException;

}