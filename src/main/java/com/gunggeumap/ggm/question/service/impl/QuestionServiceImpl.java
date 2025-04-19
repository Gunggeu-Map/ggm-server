package com.gunggeumap.ggm.question.service.impl;

import com.gunggeumap.ggm.answer.entity.Answer;
import com.gunggeumap.ggm.answer.repository.AnswerRepository;
import com.gunggeumap.ggm.question.GptAnswerParser;
import com.gunggeumap.ggm.question.dto.request.QuestionRegisterRequest;
import com.gunggeumap.ggm.question.dto.response.MapQuestionSummaryResponse;
import com.gunggeumap.ggm.question.dto.response.QuestionDetailResponse;
import com.gunggeumap.ggm.question.dto.response.QuestionSummaryResponse;
import com.gunggeumap.ggm.question.entity.Question;
import com.gunggeumap.ggm.question.entity.QuestionLike;
import com.gunggeumap.ggm.question.entity.QuestionLikeId;
import com.gunggeumap.ggm.question.enums.Category;
import com.gunggeumap.ggm.question.exception.QuestionNotFoundException;
import com.gunggeumap.ggm.question.repository.QuestionLikeRepository;
import com.gunggeumap.ggm.question.repository.QuestionRepository;
import com.gunggeumap.ggm.question.service.ChatGptService;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionServiceImpl implements QuestionService {

  private final QuestionRepository questionRepository;
  private final UserRepository userRepository;
  private final ChatGptService chatGptService;
  private final AnswerRepository answerRepository;
  private final QuestionLikeRepository questionLikeRepository;


  @Override
  public List<QuestionSummaryResponse> getTopQuestions(int size) {
    Pageable limit = PageRequest.of(0, size);
    return questionRepository.findTopWithAnswers(limit).stream()
        .map(QuestionSummaryResponse::from)
        .toList();
  }

  @Override
  @Transactional
  public Void createQuestion(QuestionRegisterRequest request) {

    User user = userRepository.findById(request.userId())
        .orElseThrow(() -> new UserNotFoundException(request.userId() + "를 찾을 수 없습니다."));

    GeometryFactory geometryFactory = new GeometryFactory();
    Point point = geometryFactory.createPoint(
        new Coordinate(request.longitude(), request.latitude()));

    Question question = questionRepository.save(request.toEntity(user, point));

    // TODO : 연령대는 일단 하드코딩함. 향 후 수정 필요.
    String prompt = """
        너는 과학에 대해 친절하고 정확하게 설명해주는 AI야.
        아래는 사용자의 과학적 질문이야. 다음 지침에 따라 답변을 생성해줘:
        
        1. **질문에 대한 설명은 약 두 문장 내외의 자연스러운 단락으로 작성**해줘.
        2. **사용자의 연령대에 맞춰 설명 난이도를 조절**해줘. (예: 10대는 쉽게, 20대 이상은 구체적으로)
        3. 질문이 속하는 과학 카테고리를 아래의 목록에서 정확히 하나 선택해줘:
           - 자연(NATURE), 우주(SPACE), 기술(TECHNOLOGY), 인체(HUMAN_BODY), 환경(ENVIRONMENT),
             물리(PHYSICS), 화학(CHEMISTRY), 생물(BIOLOGY), 지구과학(EARTH), 일상 속 궁금증(DAILY), 기타(ETC)
        4. 마지막 줄에는 **선택한 카테고리의 enum 이름**만 대문자로 출력해줘. 예: `TECHNOLOGY`
        
        ### 예시 1
        질문: 왜 하늘은 파란색인가요?
        사용자 연령대: 10대  
        답변: 햇빛이 공기 중의 작은 입자에 부딪히면 파란색 빛이 다른 색보다 더 많이 흩어져요. 그래서 우리가 보는 하늘이 파랗게 보이는 거예요.  
        카테고리: DAILY
        
        ### 예시 2
        질문: 사람은 왜 자야 하나요?
        사용자 연령대: 10대  
        답변: 잠을 자는 동안 우리 몸은 에너지를 회복하고, 뇌는 하루 동안 받은 정보를 정리해요. 잠을 잘 자야 공부도 잘 되고 몸도 건강해져요.  
        카테고리:HUMAN_BODY
        
        이제 다음 질문에 대해 답변을 해줘:
        
        질문: %s  
        사용자 연령대: 10대
        """.formatted(question.getContent());

    String gptJson = chatGptService.getChatCompletion(prompt);

    GptAnswerParser.ParsedResult result = GptAnswerParser.parse(gptJson);

    question.updateCategory(Category.valueOf(result.category.toUpperCase()));

    Answer answer = new Answer(null, question, result.content, true);

    answerRepository.save(answer);

    return null;
  }

  @Override
  public QuestionDetailResponse getQuestionDetail(Long questionId) {

    Question question = questionRepository.findQuestionById(questionId)
        .orElseThrow(() -> new QuestionNotFoundException(questionId + "를 찾을 수 없습니다."));
    return QuestionDetailResponse.from(question);
  }

  @Transactional
  @Override
  public boolean toggleQuestionLike(Long questionId, Long userId) {
    QuestionLikeId id = new QuestionLikeId(userId, questionId);

    Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> new QuestionNotFoundException("질문 없음"));

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("사용자 없음"));

    if (questionLikeRepository.existsById(id)) {
      // 이미 좋아요 되어 있음 → 삭제
      questionLikeRepository.deleteById(id);
      question.decreaseLikeCount();
      return false; // false: 좋아요가 취소됨
    } else {
      // 좋아요 안 되어 있음 → 등록
      QuestionLike like = new QuestionLike(id, user, question);
      questionLikeRepository.save(like);
      question.increaseLikeCount();
      return true; // true: 좋아요가 추가됨
    }
  }

  @Override
  public List<MapQuestionSummaryResponse> getQuestionsByUser(Long userId) {
    return questionRepository.findAllByUserIdWithAnswers(userId).stream()
        .map(MapQuestionSummaryResponse::from)
        .toList();
  }

}
