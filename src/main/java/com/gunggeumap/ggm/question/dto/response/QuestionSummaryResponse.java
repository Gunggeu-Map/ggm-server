package com.gunggeumap.ggm.question.dto.response;

import com.gunggeumap.ggm.question.entity.Question;

public record QuestionSummaryResponse(
    Long id,
    String title,
    int answerCount,
    int likeCount
) {

  public static QuestionSummaryResponse from(Question question) {
    return new QuestionSummaryResponse(
        question.getId(),
        question.getTitle(),
        question.getAnswers().size(),
        question.getLikeCount()
    );
  }
}
