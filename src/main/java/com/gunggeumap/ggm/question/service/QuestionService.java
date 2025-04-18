package com.gunggeumap.ggm.question.service;

import com.gunggeumap.ggm.question.dto.request.QuestionRegisterRequest;
import com.gunggeumap.ggm.question.dto.response.QuestionDetailResponse;
import com.gunggeumap.ggm.question.dto.response.QuestionSummaryResponse;
import java.util.List;

public interface QuestionService {

  List<QuestionSummaryResponse> getTopQuestions(int size);

  Void createQuestion(QuestionRegisterRequest request);

  QuestionDetailResponse getQuestionDetail(Long questionId);

}
