package com.ssafy.cnnect.voca.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class VocaListResponseDto {
    private Long wordListId;
    private String wordContent;
    private String wordMean;

}
