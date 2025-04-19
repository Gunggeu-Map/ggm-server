package com.gunggeumap.ggm.question.dto.response;

import com.gunggeumap.ggm.question.entity.Question;

public record MapQuestionSummaryResponse(
    Long id,
    String title,
    double latitude,
    double longitude,
    int answerCount,
    int likeCount,
    String imageUrl
) {

  public static MapQuestionSummaryResponse from(Question q) {
    return new MapQuestionSummaryResponse(
        q.getId(),
        q.getTitle(),
        q.getLocation().getY(),
        q.getLocation().getX(),
        q.getAnswers().size(),
        q.getLikeCount(),
        q.getImageUrl()
    );
  }
}
