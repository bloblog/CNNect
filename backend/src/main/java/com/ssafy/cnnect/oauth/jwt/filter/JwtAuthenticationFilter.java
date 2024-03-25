package com.ssafy.cnnect.oauth.jwt.filter;

import co.elastic.clients.elasticsearch.nodes.Http;
import com.ssafy.cnnect.exception.code.ExceptionCode;
import com.ssafy.cnnect.oauth.jwt.JwtTokenProvider;
import com.ssafy.cnnect.oauth.jwt.JwtValidationType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static com.ssafy.cnnect.oauth.jwt.JwtValidationType.*;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 1. Request Header에서 JWT 토큰 추출
        String token = resolveToken((HttpServletRequest) request);
        String url = ((HttpServletRequest) request).getRequestURI();

        // 2. validateToken으로 토큰 유효성 검사        // preflight 요청 처리
        if(CorsUtils.isPreFlightRequest((HttpServletRequest) request)){
            chain.doFilter(request, response);
        } else if (token != null && jwtTokenProvider.validateToken(token) == VALID_JWT_TOKEN) {
            // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext에 저장
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if(token != null && !url.equals("/api/oauth/refreshtoken")){
            JwtValidationType type = jwtTokenProvider.validateToken(token);
            if(type.equals(INVALID_JWT_TOKEN)) sendError(ExceptionCode.INVALID_JWT_ACCESS_TOKEN, (HttpServletResponse) response);
            if(type.equals(EXPIRED_JWT_TOKEN)) sendError(ExceptionCode.ACCESS_TOKEN_EXPIRED, (HttpServletResponse) response);
            if(type.equals(UNSUPPORTED_JWT_TOKEN)) sendError(ExceptionCode.UNSUPPORTED_JWT_ACCESS_TOKEN, (HttpServletResponse) response);
            return;
        }
        chain.doFilter(request, response);
    }

    private void sendError(ExceptionCode exceptionCode, HttpServletResponse response) throws IOException {
        response.setStatus(exceptionCode.getStatus());
        response.setHeader("Content-Type", "application/xml");
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");

        String code = exceptionCode.getCode();
        String message = exceptionCode.getMessage();
        String jsonErrorResponse = String.format("{\"code\": \"%s\", \"message\": \"%s\"}", code, message);
        response.getWriter().write(jsonErrorResponse);
    }


    // Request Header에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
