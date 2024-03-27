package com.ssafy.cnnect.userHistory.dto;

import com.ssafy.cnnect.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserHistoryRequestDto {
    private String videoId;
    private User user;
}