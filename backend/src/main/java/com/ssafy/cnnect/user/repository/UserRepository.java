package com.ssafy.cnnect.user.repository;

import com.ssafy.cnnect.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
