package com.coupons.couponmanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends ApplicationException {
    public ForbiddenException() {
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
