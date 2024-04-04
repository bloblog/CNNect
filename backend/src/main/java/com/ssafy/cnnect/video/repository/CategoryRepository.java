package com.ssafy.cnnect.video.repository;

import com.ssafy.cnnect.video.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
