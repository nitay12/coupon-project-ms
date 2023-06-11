package com.coupons.couponmanagementservice.service;

import com.coupons.couponmanagementservice.exception.EntityNotFoundException;
import com.coupons.couponmanagementservice.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);

    Category updateCategory(Category category) throws EntityNotFoundException;

    void deleteCategory(Long categoryId);

    Category getCategoryById(Long categoryId);

    List<Category> getAllCategories();

}
