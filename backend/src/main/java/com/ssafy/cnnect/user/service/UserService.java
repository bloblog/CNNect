package com.ssafy.cnnect.user.service;

import com.ssafy.cnnect.userHistory.repository.UserHistoryRepository;
import com.ssafy.cnnect.oauth.jwt.JwtValidationType;
import com.ssafy.cnnect.oauth.service.RefreshTokenService;
import com.ssafy.cnnect.oauth.token.JwtToken;
import com.ssafy.cnnect.oauth.jwt.JwtTokenProvider;
import com.ssafy.cnnect.oauth.token.RefreshToken;
import com.ssafy.cnnect.user.dto.*;
import com.ssafy.cnnect.user.entity.EmailCode;
import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.user.repository.EmailCodeRepository;
import com.ssafy.cnnect.user.repository.UserBadgeRepository;
import com.ssafy.cnnect.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final CustomUserDetailsService customUserDetailsService;

    private final UserRepository userRepository;
    private final UserHistoryRepository historyRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final RefreshTokenService refreshTokenService;
    @Autowired
    private final EmailCodeRepository emailCodeRepository;

    private static final String AUTH_CODE_PREFIX = "AuthCode ";


    public Long registUser(JoinRequestDto joinRequestDto) {
        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(joinRequestDto.getUserPassword());

        User user = userRepository.save(User.builder()
                .userEmail(joinRequestDto.getUserEmail())
                .userPassword(encodePassword)
                .userNickname(joinRequestDto.getUserNickname())
                .userLevel(1) // default 레벨 0
                .roles(new ArrayList<>(Arrays.asList("USER")))
                .build());
        return user.getUserId();
    }

    public LoginSuccessResponseDto loginUser(LoginRequestDto loginRequestDto){
        String userEmail = loginRequestDto.getUserEmail();
        String userPassword = loginRequestDto.getUserPassword();
        User user = userRepository.findByUserEmail(userEmail).get();
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
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
//        refreshTokenService.saveTokenInfo(user.get().getUserId(), jwtToken.getRefreshToken(), jwtToken.getAccessToken())

        return LoginSuccessResponseDto.builder()
                .jwtToken(jwtToken)
                .nickName(user.getUserNickname())
                .level(user.getUserLevel())
                .build();
    }

    public InfoResponseDto getUser() {
        User user = customUserDetailsService.getUserByAuthentication();
        return InfoResponseDto.builder()
                .userId(user.getUserId())
                .userNickname(user.getUserNickname())
                .userLevel(user.getUserLevel())
                .userVideoCount(historyRepository.countByUserAndHistoryStatus(user, true))
                .userBadgeCount(userBadgeRepository.countByUser(user))
            .build();
}

    @Transactional
    public JwtToken reissueToken(String refreshToken){
        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken); // refresh 보내온 유저 정보
        String userId = authentication.getName();
        System.out.println("reissue 요청한 유저 : " + userId);


        JwtValidationType type = jwtTokenProvider.validateToken(refreshToken); // refresh 유효한지 체크
        Optional<RefreshToken> findToken = refreshTokenService.findToken(userId); // redis에 저장된 토큰정보
        System.out.println(findToken);
        if(findToken.isEmpty()){
            return null;
        }else if(findToken.get().getRefreshToken().equals(refreshToken) && type == JwtValidationType.VALID_JWT_TOKEN){
            return jwtTokenProvider.generateToken(authentication);
        }

        return null;
    }

    @Transactional
    public boolean checkEmail(String email){
        return userRepository.existsByUserEmail(email);
    }

    @Transactional
    public void updateUserLevelRegister(LevelRequestDto levelRequestDto) {
        User user = userRepository.findById(levelRequestDto.getUserId()).get();
        user.updateUserLevel(levelRequestDto.getLevel());
    }

    @Transactional
    public void updateUserLevel(LevelRequestDto levelRequestDto) {
        User user = customUserDetailsService.getUserByAuthentication();
        user.updateUserLevel(levelRequestDto.getLevel());
    }
    private String createCode() {
        int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendCodeToEmail(String toEmail) throws MessagingException {
        String title = "Cnnect 이메일 인증 번호입니다.";
        String authCode = createCode();
        emailService.sendEmail(toEmail, title, authCode);
        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        emailCodeRepository.save(new EmailCode(toEmail, authCode));
    }

    public boolean varifyCode(String email, String authCode){
        System.out.println("email : ");
        System.out.println(email);
        Optional<EmailCode> redisCode = emailCodeRepository.findById(email);
        System.out.println(redisCode);
        if(!redisCode.isEmpty()){
            System.out.println(redisCode.get().getAuthCode());
        }
        return (!redisCode.isEmpty()&& redisCode.get().getAuthCode().equals(authCode));
    }

}
