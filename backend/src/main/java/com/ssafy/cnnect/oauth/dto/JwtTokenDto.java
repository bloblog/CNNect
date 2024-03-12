package com.ssafy.cnnect.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtTokenDto {
    private String accessToken;
    private String refreshToken;
}