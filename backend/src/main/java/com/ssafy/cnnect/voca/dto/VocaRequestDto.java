package com.ssafy.cnnect.voca.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VocaRequestDto {
    private String wordContent;
    private String wordMean;
}
