package com.gunggeumap.ggm.question.service.impl;

import com.gunggeumap.ggm.question.dto.request.QuestionRegisterRequest;
import com.gunggeumap.ggm.question.dto.response.QuestionDetailResponse;
import com.gunggeumap.ggm.question.dto.response.QuestionSummaryResponse;
import com.gunggeumap.ggm.question.entity.Question;
import com.gunggeumap.ggm.question.exception.QuestionNotFoundException;
import com.gunggeumap.ggm.question.repository.QuestionRepository;
import com.gunggeumap.ggm.question.service.QuestionService;
import com.gunggeumap.ggm.user.entity.User;
import com.gunggeumap.ggm.user.exception.UserNotFoundException;
import com.gunggeumap.ggm.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

  private final QuestionRepository questionRepository;
  private final UserRepository userRepository;

  @Override
  public List<QuestionSummaryResponse> getTopQuestions(int size) {
    Pageable limit = PageRequest.of(0, size);
    return questionRepository.findTopWithAnswers(limit).stream()
        .map(QuestionSummaryResponse::from)
        .toList();
  }

  @Override
  public Void createQuestion(QuestionRegisterRequest request) {

    User user = userRepository.findById(request.userId())
        .orElseThrow(() -> new UserNotFoundException(request.userId() + "를 찾을 수 없습니다."));

    GeometryFactory geometryFactory = new GeometryFactory();
    Point point = geometryFactory.createPoint(
        new Coordinate(request.longitude(), request.latitude()));

    questionRepository.save(request.toEntity(user, point));
    return null;
  }

  @Override
  public QuestionDetailResponse getQuestionDetail(Long questionId) {

    Question question = questionRepository.findQuestionById(questionId).orElseThrow(() -> new QuestionNotFoundException(questionId + "를 찾을 수 없습니다."));
    return QuestionDetailResponse.from(question);
  }

}
