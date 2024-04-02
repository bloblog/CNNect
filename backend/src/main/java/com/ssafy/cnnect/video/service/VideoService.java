package com.ssafy.cnnect.video.service;

import com.ssafy.cnnect.userHistory.dto.UserHistoryResponseDto;
import com.ssafy.cnnect.userHistory.service.UserHistoryService;
import com.ssafy.cnnect.userSentence.dto.UserSentenceGetRequestDto;
import com.ssafy.cnnect.userSentence.dto.UserSentenceResponseDto;
import com.ssafy.cnnect.userSentence.service.UserSentenceService;
import com.ssafy.cnnect.video.dto.StudySentenceResponseDto;
import com.ssafy.cnnect.video.dto.StudyVideoResponseDto;
import com.ssafy.cnnect.video.entity.Sentence;
import com.ssafy.cnnect.video.entity.Video;
import com.ssafy.cnnect.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
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
                    .startTime(script.get(i).getStart())
                    .content(script.get(i).getText())
                    .score(userSentenceResponseDto != null ? userSentenceResponseDto.getSentenceScore() : null)
                    .build();

            studySentenceResponseDtoList.add(studySentenceResponseDto);
        }

        StudyVideoResponseDto studyVideoResponseDto = StudyVideoResponseDto.builder()
                .historyId(historyId)
                .videoId(videoId)
                .videoName(video.getVideo_name())
                .level(video.getVideo_level())
                .sentenceList(studySentenceResponseDtoList)
                .wordList(video.getWord_list())
                .categoryId(video.getCategory_id())
                .build();

        return studyVideoResponseDto;
    }

    public Video findByVideoId(String videoId){
        Video video = videoRepository.findByVideoId(videoId);
        return video;
    }

    public List<Video> getRegisterVideo(){
        List<Video> videoList = new ArrayList<>(); // 9 x 3 = 27개의 videoID 저장 -> 각 카테고리별로 3개씩 뽑기
        Sort sort = Sort.by(Sort.Direction.DESC, "video_date"); // 날짜 최신순 정렬
        PageRequest pageRequest = PageRequest.of(0, 4, sort);

        for(int i = 0; i < 7; i++){
            videoList.addAll(videoRepository.findByCategoryIdOrderByVideoDateDesc((long) i, pageRequest));
        }
        Collections.shuffle(videoList);

        return videoList;
    }

}
