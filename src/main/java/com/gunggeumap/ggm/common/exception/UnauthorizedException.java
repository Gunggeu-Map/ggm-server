package com.gunggeumap.ggm.common.exception;

public abstract class UnauthorizedException extends RuntimeException {

  public UnauthorizedException(String message) {
    super(message);
  }
}
