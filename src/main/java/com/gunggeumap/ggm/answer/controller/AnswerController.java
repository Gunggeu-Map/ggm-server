package com.gunggeumap.ggm.answer.controller;


import com.gunggeumap.ggm.answer.dto.response.AnswerResponse;
import com.gunggeumap.ggm.answer.service.AnswerService;
import com.gunggeumap.ggm.common.dto.ApiResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answers")
public class AnswerController {

  private final AnswerService answerService;

  @GetMapping
  public ResponseEntity<ApiResult<List<AnswerResponse>>> getAnswers(@RequestBody Long questionId) {
    return ResponseEntity.ok(ApiResult.success(answerService.getAnswerByQuestionId(questionId)));
  }
}
