package com.ssafy.cnnect.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@Getter
@RedisHash(value = "AuthCode", timeToLive = 30 * 60 * 1000)
public class EmailCode {

    @Id
    private String email;

    private String authCode;
}
