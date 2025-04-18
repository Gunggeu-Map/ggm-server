package com.gunggeumap.ggm.question.entity;

import com.gunggeumap.ggm.user.entity.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "question_likes")
public class QuestionLike {

  @EmbeddedId
  private QuestionLikeId id;

  @MapsId("userId")
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @MapsId("questionId")
  @ManyToOne
  @JoinColumn(name = "question_id", nullable = false)
  private Question question;
}
