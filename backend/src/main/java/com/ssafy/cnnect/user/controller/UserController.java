package com.ssafy.cnnect.user.controller;

import com.ssafy.cnnect.result.ResultCode;
import com.ssafy.cnnect.result.ResultResponse;
import com.ssafy.cnnect.user.dto.JoinRequestDto;
import com.ssafy.cnnect.user.dto.LoginRequestDto;
import com.ssafy.cnnect.user.service.EmailService;
import com.ssafy.cnnect.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    @Operation(summary = "유저 회원가입")
    @PostMapping(value = "/join", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> registUser(@RequestBody JoinRequestDto joinRequestDto) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.registUser(joinRequestDto)));
    }
    @Operation(summary = "유저 로그인")
    @PostMapping(value = "/login")
    public ResponseEntity<ResultResponse> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.loginUser(loginRequestDto)));
    }

    // 추후 token 기반으로 변경
    @Operation(summary = "마이페이지 유저 정보 조회")
    @GetMapping(value = "/mypage/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getUserInfo() {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.getUser()));
    }

    @Operation(summary = "refreshToken으로 accessToken 재발급")
    @GetMapping("/refreshtoken")
    public ResponseEntity<ResultResponse> reissueToken(@RequestParam String refreshToken){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.reissueToken(refreshToken)));
    }

    @Operation(summary = "가입 이메일 중복체크")
    @GetMapping("/check/{email}")
    public ResponseEntity<ResultResponse> emailCheck(@PathVariable String email){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.checkEmail(email)));
    }

    @Operation(summary = "인증 이메일 발송")
    @PostMapping("/email/send/{email}")
    public ResponseEntity<ResultResponse> emailSend(@PathVariable String email) {
        userService.sendCodeToEmail(email);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));

    }

    @Operation(summary = "이메일 인증 확인")
    @GetMapping("/email/verification")
    public ResponseEntity<ResultResponse> emailVerification(@RequestParam String email, @RequestParam String authCode){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.varifyCode(email, authCode)));
    }
}
