package com.ssafy.cnnect.userSentence.service;

import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.user.service.CustomUserDetailsService;
import com.ssafy.cnnect.userHistory.entity.UserHistory;
import com.ssafy.cnnect.userHistory.repository.UserHistoryRepository;
import com.ssafy.cnnect.userSentence.controller.UserSentenceRequestDto;
import com.ssafy.cnnect.userSentence.dto.UserSentenceResponseDto;
import com.ssafy.cnnect.userSentence.entity.UserSentence;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSentenceService {
    private final CustomUserDetailsService customUserDetailsService;
    private final UserHistoryRepository userHistoryRepository;

    @Transactional
    public UserSentenceResponseDto createUserSentence(UserSentenceRequestDto userSentenceRequestDto) {
        User user = customUserDetailsService.getUserByAuthentication();

        UserHistory userHistory = userHistoryRepository.findByVideoIdAndUser(userSentenceRequestDto.getVideoId(), user)
                .orElseThrow(() -> new ExecutionException("The user history for the provided video ID and user does not exist."));

        UserSentence userSentence = UserSentence.builder()
                .sentenceOrder(userSentenceRequestDto.getSentenceOrder())
                .sentenceContent(userSentenceRequestDto.getSentenceContent())
                .sentenceScore(userSentenceRequestDto.getSentenceScore())
                .userHistory(userHistory)
                .build();

        userHistory.addUserSentence(userSentence);

        UserSentenceResponseDto userSentenceResponseDto = UserSentenceResponseDto.builder()
                .sentenceOrder(userSentence.getSentenceOrder())
                .sentenceContent(userSentence.getSentenceContent())
                .sentenceScore(userSentence.getSentenceScore())
                .userHistoryId(userSentence.getUserHistory().getHistoryId())
                .build();

        return userSentenceResponseDto;
    }
}
