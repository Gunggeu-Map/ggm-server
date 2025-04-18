package com.gunggeumap.ggm.answer.service;

import com.gunggeumap.ggm.answer.dto.response.AnswerResponse;
import com.gunggeumap.ggm.answer.entity.Answer;
import java.util.List;

public interface AnswerService {

  List<AnswerResponse> getAnswerByQuestionId(Long questionId);

}
