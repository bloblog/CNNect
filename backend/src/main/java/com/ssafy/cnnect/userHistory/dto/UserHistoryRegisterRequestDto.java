package com.ssafy.cnnect.userHistory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserHistoryRegisterRequestDto {
    private Long userId;
    private String videoId;
    private boolean historyStatus;
//    private LocalDate historyDate;
}
