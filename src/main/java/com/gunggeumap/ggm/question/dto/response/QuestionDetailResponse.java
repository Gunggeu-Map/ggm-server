package com.gunggeumap.ggm.question.dto.response;

import com.gunggeumap.ggm.question.entity.Question;

public record QuestionDetailResponse(

    String title,

    String category,

    // TODO : 바뀔 여지가 있음 닉네임이 되든, 아이디가 되든
    Long writerId,

    String imgUrl,

    Integer likeCount,

    String content

) {
  public static QuestionDetailResponse from(Question question) {
    return new QuestionDetailResponse(
        question.getTitle(),
        question.getCategory().toString(),
        question.getUser().getId(),
        question.getImageUrl(),
        question.getLikeCount(),
        question.getContent()
    );
  }
}
