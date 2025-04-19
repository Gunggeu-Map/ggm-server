package com.gunggeumap.ggm.question.controller;

import com.gunggeumap.ggm.auth.CustomUserDetails;
import com.gunggeumap.ggm.common.dto.ApiResult;
import com.gunggeumap.ggm.question.dto.request.QuestionRegisterRequest;
import com.gunggeumap.ggm.question.dto.response.MapQuestionSummaryResponse;
import com.gunggeumap.ggm.question.dto.response.QuestionDetailResponse;
import com.gunggeumap.ggm.question.dto.response.QuestionSummaryResponse;
import com.gunggeumap.ggm.question.service.QuestionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping
  public ResponseEntity<ApiResult<Void>> createQuestion(
      @Valid @RequestBody QuestionRegisterRequest request) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResult.success(questionService.createQuestion(request), HttpStatus.CREATED.value(),
            "질문 생성 성공"));

  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResult<QuestionDetailResponse>> getQuestionDetail(
      @PathVariable Long id) {
    return ResponseEntity.ok(ApiResult.success(questionService.getQuestionDetail(id)));
  }

  @PostMapping("/{questionId}/like")
  public ResponseEntity<ApiResult<Boolean>> toggleLike(
      @PathVariable Long questionId,
      //@AuthenticationPrincipal CustomUserDetails userDetails
      Long memberId
  ) {
    boolean liked = questionService.toggleQuestionLike(questionId, memberId);
    return ResponseEntity.ok(ApiResult.success(liked));
  }

  @GetMapping("/mine")
  public ResponseEntity<ApiResult<List<QuestionSummaryResponse>>> getMyQuestions(
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    List<QuestionSummaryResponse> result = questionService.getQuestionsByUser(userDetails.userId());
    return ResponseEntity.ok(ApiResult.success(result));
  }

}
