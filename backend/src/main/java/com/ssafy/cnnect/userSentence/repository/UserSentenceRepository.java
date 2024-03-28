package com.ssafy.cnnect.userSentence.repository;

import com.ssafy.cnnect.userSentence.entity.UserSentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSentenceRepository extends JpaRepository<UserSentence, Long> {

}