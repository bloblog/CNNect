package com.ssafy.cnnect.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class EmailRequestDto {
    private String email;
    private String authCode;
}
