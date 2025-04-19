package com.gunggeumap.ggm.answer.entity;


import com.gunggeumap.ggm.answer.enums.VoteType;
import com.gunggeumap.ggm.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "answer_votes")
public class AnswerVote {

  @EmbeddedId
  private AnswerVoteId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("answerId")
  @JoinColumn(name = "answer_id")
  private Answer answer;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private VoteType voteType;

  public void updateVote(VoteType voteType) {
    this.voteType = voteType;
  }
}
