package com.gunggeumap.ggm.answer.service.impl;

import com.gunggeumap.ggm.answer.dto.request.AnswerCreateRequest;
import com.gunggeumap.ggm.answer.dto.response.AnswerResponse;
import com.gunggeumap.ggm.answer.dto.response.VoteResponse;
import com.gunggeumap.ggm.answer.entity.Answer;
import com.gunggeumap.ggm.answer.entity.AnswerVote;
import com.gunggeumap.ggm.answer.entity.AnswerVoteId;
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
import java.util.Optional;
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

  @Override
  public VoteResponse voteAnswer(Long answerId, Long userId, VoteType voteType) {
    Answer answer = answerRepository.findById(answerId)
        .orElseThrow(() -> new IllegalArgumentException("답변 없음"));

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

    AnswerVoteId id = new AnswerVoteId(userId, answerId);
    Optional<AnswerVote> existingVoteOpt = answerVoteRepository.findById(id);

    String resultVote = null;

    if (existingVoteOpt.isPresent()) {
      AnswerVote existingVote = existingVoteOpt.get();
      if (existingVote.getVoteType() == voteType) {
        answerVoteRepository.delete(existingVote);
      } else {
        existingVote.updateVote(voteType);
        answerVoteRepository.save(existingVote);
        resultVote = voteType.name();
      }
    } else {
      AnswerVote newVote = new AnswerVote(id, user, answer, voteType);
      answerVoteRepository.save(newVote);
      resultVote = voteType.name();
    }

    long likeCount = answerVoteRepository.countByAnswerAndVoteType(answer, VoteType.UP);
    long dislikeCount = answerVoteRepository.countByAnswerAndVoteType(answer, VoteType.DOWN);

    return new VoteResponse(answerId, likeCount, dislikeCount, resultVote);
  }

}
