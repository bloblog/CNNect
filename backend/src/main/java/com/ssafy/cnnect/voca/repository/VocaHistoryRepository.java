package com.ssafy.cnnect.voca.repository;

import com.ssafy.cnnect.userHistory.entity.UserHistory;
import com.ssafy.cnnect.voca.entity.Voca;
import com.ssafy.cnnect.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VocaHistoryRepository extends JpaRepository<Voca, Long> {
    List<Voca> findAllByUser(User user);
    void deleteByWordListId(Long wordListId);
    Voca findByWordContentAndUser(String WordContent, User user);

}
