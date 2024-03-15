package com.ssafy.cnnect.badge.service;


import com.ssafy.cnnect.badge.dto.BadgeListResponseDto;
import com.ssafy.cnnect.badge.repository.BadgeRepository;
import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.user.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final CustomUserDetailsService customUserDetailsService;

    private final BadgeRepository badgeRepository;

    public List<BadgeListResponseDto> getMyBadge(){
        User user = customUserDetailsService.getUserByAuthentication();
        System.out.println("badge : " + user.getUserEmail());
        return null;
    }

    public List<BadgeListResponseDto> getAllBadge(){
        return null;
    }

    public List<BadgeListResponseDto> getNewBadge(){
        return null;
    }
}
