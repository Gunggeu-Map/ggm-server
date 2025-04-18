package com.gunggeumap.ggm.question.entity;

import com.gunggeumap.ggm.answer.entity.Answer;
import com.gunggeumap.ggm.question.enums.Category;
import com.gunggeumap.ggm.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.locationtech.jts.geom.Point;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "questions")
public class Question {

  public Question(User user, String title, String content, String imageUrl,
      Point location, boolean isPublic) {
    this.user = user;
    this.title = title;
    this.content = content;
    this.imageUrl = imageUrl;
    this.location = location;
    this.isPublic = isPublic;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(nullable = true)
  private Category category;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column
  private String imageUrl;

  @Column(columnDefinition = "geometry(Point,4326)", nullable = false)
  private Point location;

  @Column(nullable = false)
  private boolean isPublic;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private Integer likeCount;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<QuestionLike> likes = new ArrayList<>();

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Answer> answers = new ArrayList<>();

  public void updateCategory(Category category) {
    this.category = category;
  }

  public void increaseLikeCount() {
    this.likeCount++;
  }

  public void decreaseLikeCount() {
    this.likeCount--;
  }

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.likeCount = 0;
  }

}
