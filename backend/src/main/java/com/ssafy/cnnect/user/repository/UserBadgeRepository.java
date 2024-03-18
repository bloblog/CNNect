package com.ssafy.cnnect.user.repository;

import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.user.entity.UserBadge;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBadgeRepository extends CrudRepository <UserBadge, Long> {

    List<UserBadge> findAllByUser(User user);
}
