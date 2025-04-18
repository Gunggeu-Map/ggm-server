package com.gunggeumap.ggm.answer.repository;

import com.gunggeumap.ggm.answer.entity.Answer;
import com.gunggeumap.ggm.answer.entity.AnswerVote;
import com.gunggeumap.ggm.answer.entity.AnswerVoteId;
import com.gunggeumap.ggm.answer.enums.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerVoteRepository extends JpaRepository<AnswerVote, AnswerVoteId> {

  Long countByAnswerAndVoteType(Answer answer, VoteType voteType);

}
