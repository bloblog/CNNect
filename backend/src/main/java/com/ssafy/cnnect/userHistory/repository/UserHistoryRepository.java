package com.ssafy.cnnect.userHistory.repository;

import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.userHistory.entity.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    List<UserHistory> findAllByUser(User user);

    List<UserHistory> findAllByUserAndHistorySentenceNot(User suer, String historySentence);

    Long countByUserAndHistorySentenceNot(User user, String historySentence);

    Optional<UserHistory> findByVideoIdAndUser(String videoId, User user);
}