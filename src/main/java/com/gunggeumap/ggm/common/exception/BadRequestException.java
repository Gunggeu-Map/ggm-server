package com.gunggeumap.ggm.common.exception;

public abstract class BadRequestException extends RuntimeException {

  public BadRequestException(String message) {
    super(message);
  }
}

