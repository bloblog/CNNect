package com.ssafy.cnnect.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SearchScriptResponseDto
{
    private String videoId;
    private String videoName;
    private String videoDate;
    private String sentence;
}
