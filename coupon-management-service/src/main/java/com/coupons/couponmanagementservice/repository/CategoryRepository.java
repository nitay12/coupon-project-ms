package com.coupons.couponmanagementservice.repository;

import com.coupons.couponmanagementservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select (count(c) > 0) from Category c where c.title = :title")
    boolean existsByTitle(@Param("title") String title);
}