package com.gunggeumap.ggm.common.exception;

public abstract class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message) {
    super(message);
  }
}
