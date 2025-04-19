package com.gunggeumap.ggm.question.controller;

import com.gunggeumap.ggm.common.dto.ApiResult;
import com.gunggeumap.ggm.question.dto.response.MapQuestionSummaryResponse;
import com.gunggeumap.ggm.question.service.MapQuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions/map")
@RequiredArgsConstructor
public class MapQuestionController {

  private final MapQuestionService mapQuestionService;

  @GetMapping
  public ResponseEntity<ApiResult<List<MapQuestionSummaryResponse>>> getQuestionsInMapBounds(
      @RequestParam double swLat,
      @RequestParam double swLng,
      @RequestParam double neLat,
      @RequestParam double neLng
  ) {

    List<MapQuestionSummaryResponse> questionsWithinBounds = mapQuestionService.getQuestionsWithinBounds(
        swLat, swLng, neLat, neLng);

    return ResponseEntity.ok(ApiResult.success(questionsWithinBounds));
  }

  @GetMapping("/search")
  public ResponseEntity<ApiResult<List<MapQuestionSummaryResponse>>> searchQuestionsByKeyword(
      @RequestParam String keyword
  ) {
    List<MapQuestionSummaryResponse> results = mapQuestionService.searchQuestionsByKeyword(keyword);
    return ResponseEntity.ok(ApiResult.success(results));
  }

}
