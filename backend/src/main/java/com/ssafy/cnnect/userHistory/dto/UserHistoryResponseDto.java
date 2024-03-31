package com.ssafy.cnnect.userHistory.dto;

import com.ssafy.cnnect.userSentence.dto.UserSentenceResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class UserHistoryResponseDto {
    private Long historyId;
    private boolean historyStatus;
    private String historySentence;
    private Double historyTime;
    private String videoId;
    private Long userId;
    private List<UserSentenceResponseDto> userSentenceList;

}