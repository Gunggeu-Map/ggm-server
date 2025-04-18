package com.gunggeumap.ggm.question.service.impl;

import com.gunggeumap.ggm.question.dto.response.QuestionSummaryResponse;
import com.gunggeumap.ggm.question.repository.QuestionRepository;
import com.gunggeumap.ggm.question.service.QuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

  private final QuestionRepository questionRepository;

  @Override
  public List<QuestionSummaryResponse> getTopQuestions(int size) {
    Pageable limit = PageRequest.of(0, size);
    return questionRepository.findTopWithAnswers(limit).stream()
        .map(QuestionSummaryResponse::from)
        .toList();
  }
}
