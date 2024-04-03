package com.ssafy.cnnect.oauth.controller;

import com.ssafy.cnnect.oauth.jwt.JwtTokenProvider;
import com.ssafy.cnnect.oauth.service.RefreshTokenService;
import com.ssafy.cnnect.result.ResultCode;
import com.ssafy.cnnect.result.ResultResponse;
import com.ssafy.cnnect.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/oauth")
public class OauthController {
    private final RefreshTokenService refreshTokenService;

    //ResponseEntity<ResultCode>
//    @Operation(summary = "refreshToken으로 accessToken 재발급")
//    @GetMapping("/refreshtoken")
//    public ResponseEntity<ResultResponse> reissueToken(@RequestParam String refreshToken){
//        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, refreshTokenService.reissueToken(refreshToken)));
//    }
}
