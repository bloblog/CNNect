package com.ssafy.cnnect.userHistory.repository;

import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.userHistory.entity.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    List<UserHistory> findAllByUser(User user);

    List<UserHistory> findAllByUserAndHistorySentenceNotAndHistoryStatus(User user, String historySentence, boolean historyStatus);

//    @Query("SELECT COUNT(u) FROM UserHistory u WHERE u.user = :user AND u.historySentence <> :historySentence AND u.historyStatus = :historyStatus")
//    Long countByUserAndHistorySentenceNot(@Param("user")User user, @Param("historySentence")String historySentence, @Param("historyStatus")boolean historyStatus);
    Long countByUserAndHistoryStatus(User user, boolean historyStatus);

    Optional<UserHistory> findByVideoIdAndUser(String videoId, User user);
    @Query("SELECT h FROM UserHistory h WHERE h.user = :user AND h.historyStatus = false AND (h.historySentence <> 'register' OR h.historySentence IS NULL)")
    List<UserHistory> findLearningVideo(User user);
    @Query("SELECT h FROM UserHistory h WHERE h.user = :user AND h.historyStatus = true AND (h.historySentence <> 'register' OR h.historySentence IS NULL)")
    List<UserHistory> findCompletedVideo(User user);

}