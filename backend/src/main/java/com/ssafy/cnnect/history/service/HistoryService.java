package com.ssafy.cnnect.history.service;

import com.ssafy.cnnect.history.dto.HistoryRegisterRequestDto;
import com.ssafy.cnnect.history.entity.History;
import com.ssafy.cnnect.history.repository.HistoryRepository;
import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.user.repository.UserRepository;
import com.ssafy.cnnect.user.service.CustomUserDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;

    @Transactional
    public void saveRegistHistory(List<HistoryRegisterRequestDto> historyList){
        Optional<User> result = userRepository.findById(historyList.get(0).getUserId());
        if(result.isEmpty()) throw new RuntimeException();

        User user = result.get();

        for(HistoryRegisterRequestDto history : historyList){
            History saveHistory = History.builder()
                    .user(user)
                    .historyDate(LocalDate.now())
                    .historyStatus(history.isHistoryStatus())
                    .videoId(history.getVideoId())
                    .build();
            historyRepository.save(saveHistory);
        }
    }
}
