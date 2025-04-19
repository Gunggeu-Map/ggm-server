package com.gunggeumap.ggm.answer.controller;


import com.gunggeumap.ggm.answer.dto.request.AnswerCreateRequest;
import com.gunggeumap.ggm.answer.dto.response.AnswerResponse;
import com.gunggeumap.ggm.answer.service.AnswerService;
import com.gunggeumap.ggm.common.dto.ApiResult;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions/{id}/answers")
public class AnswerController {

  private final AnswerService answerService;

  @GetMapping
  public ResponseEntity<ApiResult<List<AnswerResponse>>> getAnswers(@PathVariable Long id) {
    return ResponseEntity.ok(ApiResult.success(answerService.getAnswerByQuestionId(id)));
  }

  @PostMapping
  public ResponseEntity<ApiResult<Void>> createAnswer(@Valid @RequestBody AnswerCreateRequest request,
      @PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.CREATED).body
    (ApiResult.success(answerService.createAnswer(request,id), HttpStatus.CREATED.value(), "답변 생성 성공"));
  }

}
