package com.gunggeumap.ggm.answer.service.impl;

import com.gunggeumap.ggm.answer.dto.request.AnswerCreateRequest;
import com.gunggeumap.ggm.answer.dto.response.AnswerResponse;
import com.gunggeumap.ggm.answer.entity.Answer;
import com.gunggeumap.ggm.answer.enums.VoteType;
import com.gunggeumap.ggm.answer.repository.AnswerRepository;
import com.gunggeumap.ggm.answer.repository.AnswerVoteRepository;
import com.gunggeumap.ggm.answer.service.AnswerService;
import com.gunggeumap.ggm.question.entity.Question;
import com.gunggeumap.ggm.question.exception.QuestionNotFoundException;
import com.gunggeumap.ggm.question.repository.QuestionRepository;
import com.gunggeumap.ggm.user.entity.User;
import com.gunggeumap.ggm.user.exception.UserNotFoundException;
import com.gunggeumap.ggm.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerServiceImpl implements AnswerService {

  private final AnswerRepository answerRepository;
  private final UserRepository userRepository;
  private final AnswerVoteRepository answerVoteRepository;
  private final QuestionRepository questionRepository;

  @Override
  public List<AnswerResponse> getAnswerByQuestionId(Long questionId) {

    List<Answer> answers = answerRepository.findAnswerByQuestionId(questionId);
    return answers.stream().map(answer -> {
      Long likeCount = answerVoteRepository.countByAnswerAndVoteType(answer, VoteType.UP);
      Long dislikeCount = answerVoteRepository.countByAnswerAndVoteType(answer, VoteType.DOWN);
      return AnswerResponse.from(answer, likeCount, dislikeCount);
    }).toList();

  }

  @Override
  @Transactional
  public Void createAnswer(AnswerCreateRequest request,Long questionId) {

    User user = userRepository.findById(request.writerId())
        .orElseThrow(() -> new UserNotFoundException(request.writerId() + "를 찾을 수 없습니다."));

    Question question = questionRepository.findById(questionId).orElseThrow(() -> new QuestionNotFoundException(questionId + "를 찾을 수 없습니다."));

    Answer answer = request.toEntity(user,question);

    answerRepository.save(answer);

    return null;
  }


}
