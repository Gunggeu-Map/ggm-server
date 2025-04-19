package com.gunggeumap.ggm.question.repository;

import com.gunggeumap.ggm.question.entity.Question;
import com.gunggeumap.ggm.question.enums.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {

  @Query("""
          SELECT DISTINCT q FROM Question q
          LEFT JOIN FETCH q.answers
          ORDER BY q.likeCount DESC
      """)
  List<Question> findTopWithAnswers(Pageable pageable);

  Optional<Question> findQuestionById(Long id);

  @Query("""
    SELECT DISTINCT q FROM Question q
    LEFT JOIN FETCH q.answers
    WHERE within(q.location, ST_MakeEnvelope(:swLng, :swLat, :neLng, :neLat, 4326)) = true
    """)
  List<Question> findByMapBounds(
      @Param("swLat") double swLat,
      @Param("swLng") double swLng,
      @Param("neLat") double neLat,
      @Param("neLng") double neLng
  );

  @Query("""
        SELECT DISTINCT q FROM Question q
        LEFT JOIN FETCH q.answers
        WHERE q.title ILIKE %:keyword% OR q.content ILIKE %:keyword%
      """)
  List<Question> findByKeywordContainingIgnoreCase(@Param("keyword") String keyword);

  List<Question> findByCategoryWithAnswers(@Param("category") Category category);
}
