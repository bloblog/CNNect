package com.ssafy.cnnect.userSentence.repository;

import com.ssafy.cnnect.userHistory.entity.UserHistory;
import com.ssafy.cnnect.userSentence.entity.UserSentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSentenceRepository extends JpaRepository<UserSentence, Long> {
    Optional<UserSentence> findByUserHistoryAndSentenceOrder(UserHistory userHistory, int sentenceOrder);

}