package com.ssafy.cnnect.userSentence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserSentenceGetRequestDto {
    private int sentenceOrder;
    private Long historyId;
}
