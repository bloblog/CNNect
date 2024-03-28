package com.ssafy.cnnect.userHistory.service;

import com.ssafy.cnnect.userHistory.dto.UserHistoryRegisterRequestDto;
import com.ssafy.cnnect.userHistory.dto.UserHistoryRequestDto;
import com.ssafy.cnnect.userHistory.entity.UserHistory;
import com.ssafy.cnnect.userHistory.repository.UserHistoryRepository;
import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.user.repository.UserRepository;
import com.ssafy.cnnect.user.service.CustomUserDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    private final CustomUserDetailsService userDetailsService;

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
                    .build();
            userHistoryRepository.save(saveUserHistory);
        }
    }

    @Transactional
    public void createUserHistory(String videoId) {
        User user = customUserDetailsService.getUserByAuthentication();

        UserHistory history = UserHistory.builder()
                .videoId(videoId)
                .historyStatus(false)
                .user(user)
                .userSentenceList(new ArrayList<>())
                .build();

        userHistoryRepository.save(history);
    }
}
