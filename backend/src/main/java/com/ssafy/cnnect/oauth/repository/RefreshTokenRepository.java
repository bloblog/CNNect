package com.ssafy.cnnect.oauth.repository;

import com.ssafy.cnnect.oauth.token.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken,String> {
    Optional<RefreshToken> findByAccessToken(String accessToken);
}
