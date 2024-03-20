package com.ssafy.cnnect.oauth.service;

import com.ssafy.cnnect.oauth.jwt.JwtTokenProvider;
import com.ssafy.cnnect.oauth.jwt.JwtValidationType;
import com.ssafy.cnnect.oauth.repository.RefreshTokenRepository;
import com.ssafy.cnnect.oauth.token.JwtToken;
import com.ssafy.cnnect.oauth.token.RefreshToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
//    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void saveTokenInfo(String userId, String refreshToken) {
        System.out.println("redis 저장 : " + userId + " / " + refreshToken);
        refreshTokenRepository.save(new RefreshToken(userId, refreshToken));
    }

    @Transactional
    public void removeRefreshToken(String accessToken) {
        refreshTokenRepository.findByAccessToken(accessToken)
                .ifPresent(refreshToken -> refreshTokenRepository.delete(refreshToken));
    }

//    @Transactional
//    public JwtToken reissueToken(String refreshToken){
//        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
//        JwtValidationType type = jwtTokenProvider.validateToken(refreshToken);
//        System.out.println("reissue : " + authentication.getName());
//        if(type == JwtValidationType.VALID_JWT_TOKEN){
//            return jwtTokenProvider.generateToken(authentication);
//        }
//        return null;
//    }
}
