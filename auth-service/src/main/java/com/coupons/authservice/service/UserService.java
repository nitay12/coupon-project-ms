package com.coupons.authservice.service;

import com.coupons.authservice.exceptions.EntityNotFoundException;
import com.coupons.authservice.model.User;

public interface UserService {
    User addUser(User user);

    User getUserById(Long userId);

    User updateUser(User user) throws EntityNotFoundException;

    void deleteUserById(Long userId) throws EntityNotFoundException;
}
