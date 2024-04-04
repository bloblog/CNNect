package com.ssafy.cnnect.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class LoginRequestDto {
    private String userEmail;
    private String userPassword;
}
