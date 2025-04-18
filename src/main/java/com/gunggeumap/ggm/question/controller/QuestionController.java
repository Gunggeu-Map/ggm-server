package com.gunggeumap.ggm.question.controller;

import com.gunggeumap.ggm.common.dto.ApiResult;
import com.gunggeumap.ggm.question.dto.response.QuestionSummaryResponse;
import com.gunggeumap.ggm.question.service.QuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

  private final QuestionService questionService;

  @GetMapping("/top")
  public ResponseEntity<ApiResult<List<QuestionSummaryResponse>>> getTopQuestions(
      @RequestParam(defaultValue = "5") int size
  ) {
    return ResponseEntity.ok(ApiResult.success(questionService.getTopQuestions(size)));
  }
}
