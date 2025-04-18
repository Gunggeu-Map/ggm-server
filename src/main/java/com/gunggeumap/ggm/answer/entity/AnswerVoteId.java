package com.gunggeumap.ggm.answer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class AnswerVoteId implements Serializable {
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "answer_id")
  private Long answerId;
}

