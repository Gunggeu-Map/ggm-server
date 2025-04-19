package com.gunggeumap.ggm.question.service;

import com.gunggeumap.ggm.question.dto.response.MapQuestionSummaryResponse;
import java.util.List;

public interface MapQuestionService {

  List<MapQuestionSummaryResponse> getQuestionsWithinBounds(
      double swLat, double swLng, double neLat, double neLng);

}
