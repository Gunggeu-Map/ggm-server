package com.gunggeumap.ggm.answer.repository;

import com.gunggeumap.ggm.answer.entity.Answer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Long> {

  List<Answer> findAnswerByQuestionId(Long questionId);

}
