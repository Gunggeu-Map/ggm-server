package com.gunggeumap.ggm.answer.dto.request;

import com.gunggeumap.ggm.answer.entity.Answer;
import com.gunggeumap.ggm.question.entity.Question;
import com.gunggeumap.ggm.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnswerCreateRequest(

    @NotBlank
    String content,

    // TODO : User 처리에 따라 변할 가능성이 있음.
    @NotNull
    Long writerId,

    @NotNull
    Boolean isGpt
) {
  public Answer toEntity(User user, Question question) {
    return new Answer(user,question,this.content,this.isGpt);
  }
}
