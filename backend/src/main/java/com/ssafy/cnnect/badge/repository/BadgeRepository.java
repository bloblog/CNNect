package com.ssafy.cnnect.badge.repository;

import com.ssafy.cnnect.badge.entity.Badge;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends CrudRepository<Badge, Long> {
}
