package com.ssafy.cnnect.user.repository;

import com.ssafy.cnnect.user.entity.EmailCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailCodeRepository extends CrudRepository<EmailCode, String> {
    Optional<EmailCode> findByEmail(String email);

}
