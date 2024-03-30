package com.ssafy.cnnect.userSentence.repository;

import com.ssafy.cnnect.userHistory.entity.UserHistory;
import com.ssafy.cnnect.userSentence.entity.UserSentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSentenceRepository extends JpaRepository<UserSentence, Long> {
    Optional<UserSentence> findByUserHistoryAndSentenceOrder(UserHistory userHistory, int sentenceOrder);

    @Query(value = "COUNT (*) FROM user_sentence us WHERE us.history_id = :historyId AND us.sentence_score >= 8", nativeQuery = true)
    Long countAllByHistoryIdAndSentenceScoreGreaterThanEqual(@Param("historyId") Long historyId);

//    @Query("SELECT us FROM user_sentence us WHERE us.history_id = :historyId AND us.sentence_score >= 8")
    List<UserSentence> findAllByUserHistory(UserHistory historyId);
}