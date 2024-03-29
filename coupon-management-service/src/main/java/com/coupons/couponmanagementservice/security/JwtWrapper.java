package com.coupons.couponmanagementservice.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JwtWrapper {
    private String token;

    public String getToken() {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return token;
    }
}
