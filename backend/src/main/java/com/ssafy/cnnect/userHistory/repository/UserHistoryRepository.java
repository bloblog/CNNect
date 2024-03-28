package com.ssafy.cnnect.userHistory.repository;

import com.ssafy.cnnect.userHistory.entity.UserHistory;
import com.ssafy.cnnect.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    List<UserHistory> findAllByUser(User user);
    Long countByUser(User user);
    Optional<UserHistory> findByVideoIdAndUser(String videoId, User user);
}
