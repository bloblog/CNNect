package com.ssafy.cnnect.video.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class StudyVideoResponseDto {
    private Long historyId;
    private String videoId;
    private String videoName;
    private Long level;
    private List<StudySentenceResponseDto> sentenceList;
    private List<String> wordList;
}
