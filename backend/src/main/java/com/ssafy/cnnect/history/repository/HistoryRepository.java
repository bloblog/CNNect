package com.ssafy.cnnect.history.repository;

import com.ssafy.cnnect.history.entity.History;
import com.ssafy.cnnect.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findAllByUser(User user);
    Long countByUser(User user);
}
