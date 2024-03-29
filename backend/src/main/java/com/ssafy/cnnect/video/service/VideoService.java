package com.ssafy.cnnect.video.service;

import com.ssafy.cnnect.user.service.CustomUserDetailsService;
import com.ssafy.cnnect.userHistory.dto.UserHistoryResponseDto;
import com.ssafy.cnnect.userHistory.repository.UserHistoryRepository;
import com.ssafy.cnnect.userHistory.service.UserHistoryService;
import com.ssafy.cnnect.userSentence.dto.UserSentenceGetRequestDto;
import com.ssafy.cnnect.userSentence.dto.UserSentenceResponseDto;
import com.ssafy.cnnect.userSentence.service.UserSentenceService;
import com.ssafy.cnnect.video.dto.StudySentenceResponseDto;
import com.ssafy.cnnect.video.dto.StudyVideoResponseDto;
import com.ssafy.cnnect.video.entity.Sentence;
import com.ssafy.cnnect.video.entity.Video;
import com.ssafy.cnnect.video.repository.VideoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final CustomUserDetailsService customUserDetailsService;
    private final VideoRepository videoRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final UserHistoryService userHistoryService;
    private final UserSentenceService userSentenceService;

    public Page<Video> findByCategory_idAndPage(int categoryId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return videoRepository.findByCategoryId(categoryId, pageable);
    }

    @Transactional
    public List<Video> getAllVideo(){
        List<Video> videolist = videoRepository.findAllVideo();
        return videolist;
    }

    @Transactional
    public List<Video> getCatVideo(Long categoryId){
        List<Video> videolist = videoRepository.findByCategoryId(categoryId);
        return videolist;
    }

    @Transactional
    public StudyVideoResponseDto getStudyVideo(String videoId) {
        Video video = videoRepository.findByVideoId(videoId); // 영상 정보
        List<Sentence> script = video.getSentence_list(); // 스크립트 (문장별 startTime, content 리스트)
        UserHistoryResponseDto userHistory = userHistoryService.getUserHistory(videoId); // 학습 기록 (없으면 null 반환)

        if (userHistory == null) { // 새로운 영상을 학습 시작하는 경우 (학습 기록이 없는 경우)
            userHistory = userHistoryService.createUserHistory(videoId); // 학습 기록 생성
        }

        Long historyId = userHistory.getHistoryId(); // 학습 기록 id
        List<StudySentenceResponseDto> studySentenceResponseDtoList = new ArrayList<>();

        for (int i = 0; i < script.size(); i++) {
            // i+1번째 문장 조회 (없으면 null 반환)
            UserSentenceGetRequestDto userSentenceGetRequestDto = UserSentenceGetRequestDto.builder()
                    .sentenceOrder(i + 1)
                    .historyId(historyId)
                    .build();
            UserSentenceResponseDto userSentenceResponseDto = userSentenceService.getUserSentence(userSentenceGetRequestDto);

            StudySentenceResponseDto studySentenceResponseDto = StudySentenceResponseDto.builder()
                    .order(i + 1)
                    .start(script.get(i).getStart())
                    .text(script.get(i).getText())
                    .score(userSentenceResponseDto != null ? userSentenceResponseDto.getSentenceScore() : null)
                    .build();

            studySentenceResponseDtoList.add(studySentenceResponseDto);
        }

        StudyVideoResponseDto studyVideoResponseDto = StudyVideoResponseDto.builder()
                .videoId(videoId)
                .videoName(video.getVideo_name())
                .videoLevel(video.getVideo_level())
                .sentenceList(studySentenceResponseDtoList)
                .wordList(video.getWord_list())
                .build();

        return studyVideoResponseDto;
    }
}
