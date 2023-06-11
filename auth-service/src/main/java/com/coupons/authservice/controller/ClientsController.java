package com.coupons.authservice.controller;

import com.coupons.authservice.exceptions.EntityNotFoundException;
import com.coupons.authservice.model.User;
import com.coupons.authservice.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/clients")
@CrossOrigin()
public class ClientsController {
    private final UserServiceImpl userServiceImpl;
    @GetMapping("{userId}")
    public User getUserById(@PathVariable("userId") Long userId){
        return userServiceImpl.getUserById(userId);
    }
    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId")Long userId) {
        try {
            userServiceImpl.deleteUserById(userId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
