package com.ssafy.cnnect.userHistory.service;

import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.user.repository.UserRepository;
import com.ssafy.cnnect.user.service.CustomUserDetailsService;
import com.ssafy.cnnect.userHistory.dto.UserHistoryRegisterRequestDto;
import com.ssafy.cnnect.userHistory.dto.UserHistoryResponseDto;
import com.ssafy.cnnect.userHistory.dto.UserHistoryUpdateRequestDto;
import com.ssafy.cnnect.userHistory.entity.UserHistory;
import com.ssafy.cnnect.userHistory.repository.UserHistoryRepository;
import com.ssafy.cnnect.userSentence.dto.UserSentenceResponseDto;
import com.ssafy.cnnect.userSentence.entity.UserSentence;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserHistoryService {
    private final CustomUserDetailsService customUserDetailsService;
    private final UserHistoryRepository userHistoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveRegistHistory(List<UserHistoryRegisterRequestDto> historyList){
        Optional<User> result = userRepository.findById(historyList.get(0).getUserId());
        if(result.isEmpty()) throw new RuntimeException();

        User user = result.get();

        for(UserHistoryRegisterRequestDto history : historyList){
            UserHistory saveUserHistory = UserHistory.builder()
                    .user(user)
                    .historyStatus(history.isHistoryStatus())
                    .videoId(history.getVideoId())
                    .historySentence(history.getHistorySentence())
                    .build();
            userHistoryRepository.save(saveUserHistory);
        }
    }

    @Transactional
    public UserHistoryResponseDto createUserHistory(String videoId) {
        User user = customUserDetailsService.getUserByAuthentication();

        Optional<UserHistory> OptionalUserHistory = userHistoryRepository.findByVideoIdAndUser(videoId, user);

        if (OptionalUserHistory.isPresent()) {
            throw new RuntimeException("User history for the specified video already exists.");
        }

        UserHistory userHistory = UserHistory.builder()
                .historyStatus(false)
                .historySentence(null)
                .historyTime(null)
                .videoId(videoId)
                .user(user)
                .userSentenceList(new ArrayList<>())
                .build();

        userHistoryRepository.save(userHistory);

        return UserHistoryResponseDto.builder()
                .historyId(userHistory.getHistoryId())
                .historyStatus(userHistory.isHistoryStatus())
                .historySentence(userHistory.getHistorySentence())
                .historyTime(userHistory.getHistoryTime())
                .videoId(userHistory.getVideoId())
                .userId(userHistory.getUser().getUserId())
                .userSentenceList(new ArrayList<>())
                .build();
    }

    @Transactional
    public UserHistoryResponseDto getUserHistory(String videoId) {
        User user = customUserDetailsService.getUserByAuthentication();
        Optional<UserHistory> OptionalUserHistory = userHistoryRepository.findByVideoIdAndUser(videoId, user);

        if (OptionalUserHistory.isEmpty()) return null;

        UserHistory userHistory = OptionalUserHistory.get();
        List<UserSentence> userSentenceList = userHistory.getUserSentenceList();

        List<UserSentenceResponseDto> userSentenceResponseDtoList = userSentenceList.stream()
                .map(userSentence -> UserSentenceResponseDto.builder()
                        .sentenceOrder(userSentence.getSentenceOrder())
                        .sentenceContent(userSentence.getSentenceContent())
                        .sentenceScore(userSentence.getSentenceScore())
                        .userHistoryId(userSentence.getUserHistory().getHistoryId())
                        .build())
                .toList();

        return UserHistoryResponseDto.builder()
                .historyId(userHistory.getHistoryId())
                .historyStatus(userHistory.isHistoryStatus())
                .historySentence(userHistory.getHistorySentence())
                .historyTime(userHistory.getHistoryTime())
                .videoId(userHistory.getVideoId())
                .userId(userHistory.getUser().getUserId())
                .userSentenceList(userSentenceResponseDtoList)
                .build();
    }

    @Transactional
    public void updateUserHistory(UserHistoryUpdateRequestDto userHistoryUpdateRequestDto) {
        User user = customUserDetailsService.getUserByAuthentication();

        UserHistory userHistory = userHistoryRepository.findByVideoIdAndUser(userHistoryUpdateRequestDto.getVideoId(), user)
                .orElseThrow(() -> new ExecutionException("User history not found"));

        userHistory.updateUserHistoryLast(userHistoryUpdateRequestDto.getHistorySentence(), userHistoryUpdateRequestDto.getHistoryTime());
    }
}
