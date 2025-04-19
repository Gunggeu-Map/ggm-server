package com.gunggeumap.ggm.question.repository;

import com.gunggeumap.ggm.question.entity.QuestionLike;
import com.gunggeumap.ggm.question.entity.QuestionLikeId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionLikeRepository extends JpaRepository<QuestionLike, QuestionLikeId> {

  boolean existsById(QuestionLikeId id);

}
