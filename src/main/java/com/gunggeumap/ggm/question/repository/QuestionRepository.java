package com.gunggeumap.ggm.question.repository;

import com.gunggeumap.ggm.question.entity.Question;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {

  @Query("""
          SELECT DISTINCT q FROM Question q
          LEFT JOIN FETCH q.answers
          ORDER BY q.likeCount DESC
      """)
  List<Question> findTopWithAnswers(Pageable pageable);

  Optional<Question> findQuestionById(Long id);
}
