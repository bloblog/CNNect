package com.ssafy.cnnect.user.service;

import com.ssafy.cnnect.exception.UnAuthorizedException;
import com.ssafy.cnnect.exception.code.ExceptionCode;
import com.ssafy.cnnect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        System.out.println("userEmail : " + userEmail);
        return userRepository.findByUserEmail(userEmail)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
    private UserDetails createUserDetails(com.ssafy.cnnect.user.entity.User user) {
        System.out.println("user Info : " + user.getUserEmail()  + " " + user.getUserPassword()
                + " " + user.getRoles());
        UserDetails ud = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
        System.out.println(ud);
        return ud;
    }
    public com.ssafy.cnnect.user.entity.User getUserByAuthentication() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUserEmail(authentication.getName())
                .orElseThrow(()-> new UnAuthorizedException(ExceptionCode.AUTHENTICATION_CODE_EXPIRED));
    }

}