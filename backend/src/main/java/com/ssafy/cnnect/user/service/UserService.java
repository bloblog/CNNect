package com.ssafy.cnnect.user.service;

import com.ssafy.cnnect.oauth.dto.JwtTokenDto;
import com.ssafy.cnnect.oauth.jwt.JwtTokenProvider;
import com.ssafy.cnnect.user.dto.InfoResponseDto;
import com.ssafy.cnnect.user.dto.JoinRequestDto;
import com.ssafy.cnnect.user.dto.LoginRequestDto;
import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public Long registUser(JoinRequestDto joinRequestDto) {
        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(joinRequestDto.getUserPassword());

        User user = userRepository.save(User.builder()
                .userEmail(joinRequestDto.getUserEmail())
                .userPassword(encodePassword)
                .userNickname(joinRequestDto.getUserNickname())
                .userLevel(0) // default 레벨 0
                .build());
        return user.getUserId();
    }

    public JwtTokenDto loginUser(LoginRequestDto loginRequestDto){
        String userEmail = loginRequestDto.getUserEmail();
        String userPassword = loginRequestDto.getUserPassword();
        System.out.println(1111111);
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEmail, userPassword);

        System.out.println(222222);
        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        System.out.println(3333333);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtTokenDto jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }

    public InfoResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId).get();
        return InfoResponseDto.builder()
                .userId(user.getUserId())
                .userEmail(user.getUserEmail())
                .userPassword(user.getUserPassword())
                .userNickname(user.getUserNickname())
                .userLevel(user.getUserLevel())
                .build();
    }
}
