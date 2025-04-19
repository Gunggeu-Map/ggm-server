package com.gunggeumap.ggm.answer.dto.response;

import com.gunggeumap.ggm.answer.entity.Answer;
import com.gunggeumap.ggm.answer.enums.VoteType;
import java.time.LocalDateTime;

public record AnswerResponse(
    String writer,
    String content,
    LocalDateTime createdAt,
    Long likeCount,
    Long dislikeCount,
    Boolean isGpt,
    VoteType userVote
) {

  // Answer에서 정보를 가져오고, likeCount랑 dislikeCount는 AnswerVote에서 가져와야하니깐.
  public static AnswerResponse from(Answer answer, Long likeCount, Long dislikeCount, VoteType userVote) {
    String writerNickname = answer.isGpt() ? "GPT" : answer.getWriter().getNickname();
    return new AnswerResponse(
        writerNickname,
        answer.getContent(),
        answer.getCreatedAt(),
        likeCount,
        dislikeCount,
        answer.isGpt(),
        userVote
    );
  }
}
