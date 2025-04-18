package com.gunggeumap.ggm.question.exception;
import com.gunggeumap.ggm.common.exception.ResourceNotFoundException;

public class QuestionNotFoundException extends ResourceNotFoundException {

  public QuestionNotFoundException(String message) {
    super(message);
  }

}
