package com.gunggeumap.ggm.user.exception;

import com.gunggeumap.ggm.common.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {

  public UserNotFoundException(String message) {
    super(message);
  }


}
