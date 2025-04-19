package com.gunggeumap.ggm.question.service.impl;

import com.gunggeumap.ggm.question.dto.response.MapQuestionSummaryResponse;
import com.gunggeumap.ggm.question.entity.Question;
import com.gunggeumap.ggm.question.repository.QuestionRepository;
import com.gunggeumap.ggm.question.service.MapQuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapQuestionServiceImpl implements MapQuestionService {

  private final QuestionRepository questionRepository;

  @Override
  public List<MapQuestionSummaryResponse> getQuestionsWithinBounds(double swLat, double swLng,
      double neLat, double neLng) {

    return questionRepository.findByMapBounds(swLat, swLng, neLat, neLng).stream()
        .map(MapQuestionSummaryResponse::from)
        .toList();
  }

  @Override
  public List<MapQuestionSummaryResponse> searchQuestionsByKeyword(String keyword) {

    return questionRepository.findByKeywordContainingIgnoreCase(keyword).stream()
        .map(MapQuestionSummaryResponse::from)
        .toList();
  }

  @Override
  public List<MapQuestionSummaryResponse> searchQuestionsByCategory(String category) {
    return List.of();
  }

}
