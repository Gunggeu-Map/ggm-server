package com.gunggeumap.ggm.user.entity;

import com.gunggeumap.ggm.question.entity.QuestionLike;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"provider", "provider_id"})
})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String provider;

  @Column(name = "provider_id", nullable = false)
  private String providerId;

  @Column(nullable = false, unique = true)
  private String nickname;

  @Column(nullable = false)
  private LocalDate birth;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<QuestionLike> likedQuestions = new ArrayList<>();

  @Builder
  private User(String provider, String providerId, String nickname, LocalDate birth) {
    this.provider = provider;
    this.providerId = providerId;
    this.nickname = nickname;
    this.birth = birth;
    this.createdAt = LocalDateTime.now();
  }
}
