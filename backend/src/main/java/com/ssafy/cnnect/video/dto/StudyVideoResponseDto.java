package com.ssafy.cnnect.video.dto;

import com.ssafy.cnnect.video.entity.Sentence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class StudyVideoResponseDto {
    private String videoId;
    private String videoName;
    private Long videoLevel;
    private List<StudySentenceResponseDto> sentenceList;
    private List<String> wordList;
}
