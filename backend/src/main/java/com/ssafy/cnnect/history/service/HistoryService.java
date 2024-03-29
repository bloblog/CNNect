package com.ssafy.cnnect.history.service;

import com.ssafy.cnnect.history.dto.HistoryRegisterRequestDto;
import com.ssafy.cnnect.history.dto.HistoryResponseDto;
import com.ssafy.cnnect.history.entity.History;
import com.ssafy.cnnect.history.repository.HistoryRepository;
import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.user.repository.UserRepository;
import com.ssafy.cnnect.user.service.CustomUserDetailsService;
import com.ssafy.cnnect.video.entity.Video;
import com.ssafy.cnnect.video.repository.VideoRepository;
import com.ssafy.cnnect.video.service.VideoService;
import com.ssafy.cnnect.voca.dto.VocaListResponseDto;
import com.ssafy.cnnect.voca.entity.Voca;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;
    private final VideoService videoService;

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

    public List<HistoryResponseDto> getLearningVideo(){
        User user = userDetailsService.getUserByAuthentication();
        List<History> learningVideoList = historyRepository.findLearningVideo(user);

        List<HistoryResponseDto> videoList = learningVideoList.stream()
                .map(history -> {
                    Video video = videoService.findByVideoId(history.getVideoId());
                    if (video != null) {
                        return HistoryResponseDto.builder()
                                .videoName(video.getVideo_name())
                                .historyId(history.getHistoryId())
                                .videoId(history.getVideoId())
                                .lastSentence(history.getHistorySentence())
                                .videoLevel(video.getVideo_level())
//                                .completedSentenceNum(video.getUserSentenceList.size())
                                .totalSentenceNum(video.getSentence_list().size())
                                .build();
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return videoList;
    }

    public List<HistoryResponseDto> getCompletedVideo(){
        User user = userDetailsService.getUserByAuthentication();
        List<History> completedVideoList = historyRepository.findCompletedVideo(user);

        List<HistoryResponseDto> videoList = completedVideoList.stream()
                .map(history -> {
                    Video video = videoService.findByVideoId(history.getVideoId());
                    if (video != null) {
                        return HistoryResponseDto.builder()
                                .videoName(video.getVideo_name())
                                .historyId(history.getHistoryId())
                                .videoId(history.getVideoId())
                                .lastSentence(history.getHistorySentence())
                                .videoLevel(video.getVideo_level())
//                                .completedSentenceNum(video.getUserSentenceList.size())
                                .totalSentenceNum(video.getSentence_list().size())
                                .build();
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return videoList;
    }
}
