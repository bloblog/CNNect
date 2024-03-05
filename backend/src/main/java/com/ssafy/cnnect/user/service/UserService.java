package com.ssafy.cnnect.user.service;

import com.ssafy.cnnect.user.dto.InfoResponseDto;
import com.ssafy.cnnect.user.dto.JoinRequestDto;
import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Long registUser(JoinRequestDto joinRequestDto) {
        User user = userRepository.save(User.builder()
                .userEmail(joinRequestDto.getUserEmail())
                .userPassword(joinRequestDto.getUserPassword())
                .userNickname(joinRequestDto.getUserNickname())
                .userLevel(0) // default 레벨 0
                .build());
        return user.getUserId();
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
