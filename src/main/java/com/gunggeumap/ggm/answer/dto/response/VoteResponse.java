package com.gunggeumap.ggm.answer.dto.response;

public record VoteResponse(
    Long answerId,
    Long likeCount,
    Long dislikeCount,
    String userVote
) {

}
