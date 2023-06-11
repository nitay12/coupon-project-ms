package com.coupons.couponmanagementservice.service;

import com.coupons.couponmanagementservice.exception.EntityNotFoundException;
import com.coupons.couponmanagementservice.exception.ForbiddenException;
import com.coupons.couponmanagementservice.exception.TitleExistException;
import com.coupons.couponmanagementservice.model.Coupon;
import com.coupons.couponmanagementservice.messaging.producer.CouponEventProducer;
import com.coupons.couponmanagementservice.repository.CategoryRepository;
import com.coupons.couponmanagementservice.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    private final CategoryRepository categoryRepository;
    private final CouponEventProducer couponEventProducer;

    private void ifCouponNotExistOrForbiddenThrowExceptions(Long couponId, Long companyId) throws EntityNotFoundException, ForbiddenException {
        if (!couponRepository.existsById(couponId)) {
            throw new EntityNotFoundException("Coupon", couponId);
        }
        if (!couponRepository.existsByIdAndCompanyId(couponId, companyId)) {
            throw new ForbiddenException("Forbidden");
        }
    }

    @Override
    public Coupon createCoupon(Coupon coupon, Long companyId) throws TitleExistException {
        if (couponRepository.existsByTitleIgnoreCaseAndCompanyId(coupon.getTitle(), coupon.getCompanyId())) {
            throw new TitleExistException("This title is already exist");
        }
        coupon.setCompanyId(companyId);
        final Coupon createdCoupon = couponRepository.saveAndFlush(coupon);
        couponEventProducer.sendCouponCreatedEvent(createdCoupon);
        return createdCoupon;
    }

    @Override
    public Coupon updateCoupon(Coupon coupon, Long companyId) throws TitleExistException, ForbiddenException, EntityNotFoundException {

        ifCouponNotExistOrForbiddenThrowExceptions(coupon.getId(), companyId);
        if (
                couponRepository.existsByTitleIgnoreCaseAndCompanyId(coupon.getTitle(), companyId)
        ) {
            throw new TitleExistException("This title is already exist");
        }
        return couponRepository.saveAndFlush(coupon);
    }


    @Override
    public void deleteCoupon(Long couponId, Long companyId) throws EntityNotFoundException, ForbiddenException {
        ifCouponNotExistOrForbiddenThrowExceptions(couponId, companyId);
        couponEventProducer.sendCouponDeletedEvent(couponId);
        couponRepository.deleteById(couponId);
    }

    @Override
    public void deleteCoupon(Long couponId) {
        if (couponRepository.existsById(couponId)) {
            couponRepository.deleteById(couponId);
            couponEventProducer.sendCouponDeletedEvent(couponId);
        }
    }

    @Override
    public void deleteAllCompanyCoupons(Long companyId) {
        couponRepository.deleteByCompanyId(companyId);
    }

    @Override
    public Coupon getCouponById(Long couponId) throws EntityNotFoundException {
        return couponRepository
                .findById(couponId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public List<Coupon> getAllCoupons(String categoryTitle) throws EntityNotFoundException {
        if (!categoryRepository.existsByTitle(categoryTitle)) {
            throw new EntityNotFoundException(categoryTitle + " is not a recognized category");
        }
        return couponRepository.findByCategories_TitleIs(categoryTitle);
    }

    @Override
    public List<Coupon> getAllCompanyCoupons(Long companyId) {
        return couponRepository.findByCompanyId(companyId);
    }

    @Override
    public List<Coupon> getAllCompanyCoupons(Long companyId, String categoryTitle) {
        return couponRepository.findByCompanyIdAndCategories_Title(companyId, categoryTitle);
    }

    @Override
    public List<Coupon> getValidCoupons() {
        return couponRepository.findByExpirationDateIsBefore(LocalDate.now());
    }

    @Override
    public boolean isCouponValid(Long couponId) throws EntityNotFoundException {
        final Coupon coupon = couponRepository.findById(couponId).orElseThrow(EntityNotFoundException::new);
        return coupon.getExpirationDate().isBefore(LocalDate.now());
    }

    @Override
    public boolean isCouponExpired(Long couponId) throws EntityNotFoundException {
        final Coupon coupon = couponRepository.findById(couponId).orElseThrow(EntityNotFoundException::new);
        return coupon.getExpirationDate().isAfter(LocalDate.now());
    }
}
