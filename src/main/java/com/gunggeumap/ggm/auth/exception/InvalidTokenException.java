package com.gunggeumap.ggm.auth.exception;

import com.gunggeumap.ggm.common.exception.UnauthorizedException;

public class InvalidTokenException extends UnauthorizedException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
