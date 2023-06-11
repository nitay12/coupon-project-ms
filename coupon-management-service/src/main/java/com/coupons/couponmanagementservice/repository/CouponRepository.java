package com.coupons.couponmanagementservice.repository;

import com.coupons.couponmanagementservice.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    void deleteByCompanyId(Long companyId);
    @Query("select (count(c) > 0) from Coupon c where upper(c.title) = upper(?1) and c.companyId = ?2")
    boolean existsByTitleIgnoreCaseAndCompanyId(String title, Long companyId);

    @Query("select (count(c) > 0) from Coupon c where c.id = ?1 and c.companyId = ?2")
    boolean existsByIdAndCompanyId(Long id, Long companyId);

    @Query("select c from Coupon c inner join c.categories categories where categories.title = :title")
    List<Coupon> findByCategories_TitleIs(@Param("title") String title);

    List<Coupon> findByCompanyIdAndCategories_Title(Long companyId, String title);

    List<Coupon> findByExpirationDateIsBefore(LocalDate expirationDate);

    @Query("select c from Coupon c where c.companyId = :companyId")
    List<Coupon> findByCompanyId(@Param("companyId") Long companyId);



}