package com.coupons.couponmanagementservice.clr;

import com.coupons.couponmanagementservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class InitJwtForTest implements CommandLineRunner {
    private final JwtService jwtService;
    private final Map<String, Object> claims = new HashMap<>();
    private final List<String> roles = new ArrayList<>();

    @Override
    public void run(String... args) {
        roles.add("ROLE_COMPANY");
        claims.put("roles", roles);
        String token = jwtService.createToken(claims, "1");
        log.info("COMPANY JWT: " + token);
        roles.clear();
        claims.clear();
        roles.add("ROLE_CUSTOMER");
        claims.put("roles", roles);
        token = jwtService.createToken(claims, "1");
        log.info("CUSTOMER JWT: " + token);
    }
}
