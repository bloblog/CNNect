package com.ssafy.cnnect.userHistory.repository;

import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.userHistory.entity.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    List<UserHistory> findAllByUser(User user);
    Long countByUser(User user);
    Optional<UserHistory> findByVideoIdAndUser(String videoId, User user);
    @Query("SELECT h FROM UserHistory h WHERE h.user = :user AND h.historyStatus = false AND h.historySentence <> 'Register'")
    List<UserHistory> findLearningVideo(User user);
    @Query("SELECT h FROM UserHistory h WHERE h.user = :user AND h.historyStatus = true AND h.historySentence <> 'Register'")
    List<UserHistory> findCompletedVideo(User user);

}