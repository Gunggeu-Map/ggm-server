package com.gunggeumap.ggm.answer.service.impl;

import com.gunggeumap.ggm.answer.dto.response.AnswerResponse;
import com.gunggeumap.ggm.answer.entity.Answer;
import com.gunggeumap.ggm.answer.enums.VoteType;
import com.gunggeumap.ggm.answer.repository.AnswerRepository;
import com.gunggeumap.ggm.answer.repository.AnswerVoteRepository;
import com.gunggeumap.ggm.answer.service.AnswerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

  private final AnswerRepository answerRepository;

  private final AnswerVoteRepository answerVoteRepository;

  
  @Override
  public List<AnswerResponse> getAnswerByQuestionId(Long questionId) {

    List<Answer> answers = answerRepository.findAnswerByQuestionId(questionId);
    return answers.stream().map(answer -> {
      Long likeCount = answerVoteRepository.countByAnswerAndVoteType(answer, VoteType.UP);
      Long dislikeCount = answerVoteRepository.countByAnswerAndVoteType(answer, VoteType.DOWN);
      return AnswerResponse.from(answer, likeCount, dislikeCount);
    }).toList();

  }


}
