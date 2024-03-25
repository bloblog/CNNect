package com.ssafy.cnnect.video.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class VideoListResponseDto {
    private String videoId;
    private String videoName;
//    private String videoUrl;
    private int videoLevel;
    private String videoThumbnail;
    private Date videoDate;
    private List<String> sentenceList;
    private List<String> wordList;
    private int categoryId;
}
