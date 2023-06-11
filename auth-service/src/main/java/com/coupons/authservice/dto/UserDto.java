package com.coupons.authservice.dto;

import java.io.Serializable;

public record UserDto(Long id, String username, String password, boolean enabled) implements Serializable {
}
