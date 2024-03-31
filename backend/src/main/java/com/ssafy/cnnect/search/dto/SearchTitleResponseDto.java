package com.ssafy.cnnect.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SearchTitleResponseDto {
    private String videoId;
    private String videoName;
    private Long videoLevel;
    private String videoDate;
    private String videoThumbnail;
}
