package com.ssafy.cnnect.userSentence.service;

import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.user.service.CustomUserDetailsService;
import com.ssafy.cnnect.userHistory.entity.UserHistory;
import com.ssafy.cnnect.userHistory.repository.UserHistoryRepository;
import com.ssafy.cnnect.userSentence.dto.UserSentenceCreateRequestDto;
import com.ssafy.cnnect.userSentence.dto.UserSentenceGetRequestDto;
import com.ssafy.cnnect.userSentence.dto.UserSentenceResponseDto;
import com.ssafy.cnnect.userSentence.dto.UserSentenceUpdateRequestDto;
import com.ssafy.cnnect.userSentence.entity.UserSentence;
import com.ssafy.cnnect.userSentence.repository.UserSentenceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSentenceService {
    private final CustomUserDetailsService customUserDetailsService;
    private final UserHistoryRepository userHistoryRepository;
    private final UserSentenceRepository userSentenceRepository;

    @Transactional
    public UserSentenceResponseDto createUserSentence(UserSentenceCreateRequestDto userSentenceCreateRequestDto) {
        User user = customUserDetailsService.getUserByAuthentication();

        UserHistory userHistory = userHistoryRepository.findByVideoIdAndUser(userSentenceCreateRequestDto.getVideoId(), user)
                .orElseThrow(() -> new ExecutionException("The user history for the provided video ID and user does not exist."));

        UserSentence userSentence = UserSentence.builder()
                .sentenceOrder(userSentenceCreateRequestDto.getSentenceOrder())
                .sentenceContent(userSentenceCreateRequestDto.getSentenceContent())
                .sentenceScore(userSentenceCreateRequestDto.getSentenceScore())
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

    @Transactional
    public UserSentenceResponseDto getUserSentence(UserSentenceGetRequestDto userSentenceGetRequestDto) {

        UserHistory userHistory = userHistoryRepository.findById(userSentenceGetRequestDto.getHistoryId())
                .orElseThrow(() -> new ExecutionException("User history not found"));

        Optional<UserSentence> optionalUserSentence = userSentenceRepository.findByUserHistoryAndSentenceOrder(userHistory, userSentenceGetRequestDto.getSentenceOrder());

        if (optionalUserSentence.isEmpty()) return null;

        UserSentence userSentence = optionalUserSentence.get();

        UserSentenceResponseDto userSentenceResponseDto = UserSentenceResponseDto.builder()
                .sentenceOrder(userSentence.getSentenceOrder())
                .sentenceContent(userSentence.getSentenceContent())
                .sentenceScore(userSentence.getSentenceScore())
                .userHistoryId(userSentence.getUserHistory().getHistoryId())
                .build();

        return userSentenceResponseDto;
    }

    @Transactional
    public UserSentenceResponseDto updateUserSentence(UserSentenceUpdateRequestDto userSentenceUpdateRequestDto) {
        UserHistory userHistory = userHistoryRepository.findById(userSentenceUpdateRequestDto.getHistoryId())
                .orElseThrow(() -> new ExecutionException("User history not found"));

        UserSentence userSentence = userSentenceRepository.findByUserHistoryAndSentenceOrder(userHistory, userSentenceUpdateRequestDto.getSentenceOrder())
                .orElseThrow(() -> new ExecutionException("User sentence not found"));

        userSentence.updateUserSentenceScore(userSentenceUpdateRequestDto.getSentenceScore());

        UserSentenceResponseDto userSentenceResponseDto = UserSentenceResponseDto.builder()
                .sentenceOrder(userSentence.getSentenceOrder())
                .sentenceContent(userSentence.getSentenceContent())
                .sentenceScore(userSentence.getSentenceScore())
                .userHistoryId(userSentence.getUserHistory().getHistoryId())
                .build();

        return userSentenceResponseDto;
    }


}
