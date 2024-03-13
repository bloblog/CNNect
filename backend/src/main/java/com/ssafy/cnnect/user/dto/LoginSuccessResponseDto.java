package com.ssafy.cnnect.user.dto;

import com.ssafy.cnnect.oauth.token.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class LoginSuccessResponseDto {
    private JwtToken jwtToken;
    private String nickName;
    private int level;
}
