package com.gunggeumap.ggm.user.service.impl;

import com.gunggeumap.ggm.answer.repository.AnswerRepository;
import com.gunggeumap.ggm.question.repository.QuestionRepository;
import com.gunggeumap.ggm.user.dto.response.UserMypageResponse;
import com.gunggeumap.ggm.user.entity.User;
import com.gunggeumap.ggm.user.exception.UserNotFoundException;
import com.gunggeumap.ggm.user.repository.UserRepository;
import com.gunggeumap.ggm.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final QuestionRepository questionRepository;
  private final AnswerRepository answerRepository;


  @Override
  public UserMypageResponse getUserMypage(Long userId) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId + "를 찾을 수 없습니다."));

    Long questionCount = questionRepository.countByUserId(userId);

    Long answerCount = answerRepository.countByUserId(userId);

    return UserMypageResponse.from(user.getNickname(), questionCount, answerCount);

  }
}
