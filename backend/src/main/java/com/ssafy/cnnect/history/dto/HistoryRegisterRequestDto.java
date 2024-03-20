package com.ssafy.cnnect.history.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class HistoryRegisterRequestDto {
    private Long userId;
    private String videoId;
    private boolean historyStatus;
//    private LocalDate historyDate;
}
