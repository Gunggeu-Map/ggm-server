package com.gunggeumap.ggm.answer.repository;

import com.gunggeumap.ggm.answer.entity.Answer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnswerRepository extends JpaRepository<Answer,Long> {

  @Query("SELECT a FROM Answer a LEFT JOIN FETCH a.writer WHERE a.question.id = :questionId")
  List<Answer> findAnswerByQuestionId(Long questionId);

}
