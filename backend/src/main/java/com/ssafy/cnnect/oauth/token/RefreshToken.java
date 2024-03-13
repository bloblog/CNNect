package com.ssafy.cnnect.oauth.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@AllArgsConstructor
@Getter
@RedisHash(value = "jwtToken", timeToLive = 60*60*24*3)
public class RefreshToken {

    @Id
    private String Id;

    private String refreshToken;


}
