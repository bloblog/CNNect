package com.ssafy.cnnect.userHistory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserHistoryUpdateRequestDto {
    private String historySentence;
    private Double historyTime;
    private String videoId;
}