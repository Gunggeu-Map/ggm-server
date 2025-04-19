package com.gunggeumap.ggm.user.dto.response;

public record UserMypageResponse(
    String nickName,
    Long questionCount,
    Long answerCount
) {
  public static UserMypageResponse from(String nickName, Long questionCount, Long answerCount) {
    return new UserMypageResponse(nickName, questionCount, answerCount);
  }
}
