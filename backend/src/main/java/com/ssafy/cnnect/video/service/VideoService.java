package com.ssafy.cnnect.video.service;

import com.ssafy.cnnect.userHistory.entity.UserHistory;
import com.ssafy.cnnect.userHistory.repository.UserHistoryRepository;
import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.user.service.CustomUserDetailsService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final CustomUserDetailsService customUserDetailsService;
    private final VideoRepository videoRepository;
//    private final CategoryRepository categoryRepository;
    private final UserHistoryRepository userHistoryRepository;

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
    public StudyVideoResponseDto getStudyVideo(String videoId){

        User user = customUserDetailsService.getUserByAuthentication();
        Optional<UserHistory> userHistory = userHistoryRepository.findByVideoIdAndUser(videoId, user);

        Video video = videoRepository.findByVideoId(videoId);
        List<Sentence> sentenceList = video.getSentence_list();

        List<StudySentenceResponseDto> studySentenceResponseDtoList = sentenceList.stream()
                .map(sentence -> StudySentenceResponseDto.builder()
//                        .sentenceId(Long.parseLong(sentence.getSentenceId().toString(), 16))
                        .start(sentence.getStart())
                        .text(sentence.getText())
                        .score(null)
                        .build())
                .collect(Collectors.toList());

        if (userHistory.isPresent()) {
//            History History = userHistory.get();

        } else {
            UserHistory history = UserHistory.builder()
                    .videoId(videoId)
                    .historyStatus(false)
                    .user(user)
                    .userSentenceList(new ArrayList<>())
                    .build();
            userHistoryRepository.save(history);
        }

        StudyVideoResponseDto studyVideoResponseDto = StudyVideoResponseDto.builder()
                .videoId(video.getVideo_id())
                .videoName(video.getVideo_name())
                .videoLevel(video.getVideo_level())
                .sentenceList(studySentenceResponseDtoList)
                .wordList(video.getWord_list())
                .build();

        return studyVideoResponseDto;
    }
}
