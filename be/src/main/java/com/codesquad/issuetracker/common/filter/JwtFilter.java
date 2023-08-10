package com.codesquad.issuetracker.common.filter;

import com.codesquad.issuetracker.common.exception.JwtExceptionResponse;
import com.codesquad.issuetracker.common.exception.JwtExceptionType;
import com.codesquad.issuetracker.jwt.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

@RequiredArgsConstructor
@Component
public class JwtFilter implements Filter {
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String MEMBER_ID = "memberId";
    private static final String[] whiteListUris = {
            "/api/sign-up/{provider}",
            "/api/oauth/**",
            "/api/sign-In",
            "/api/reissue-access-token",
            //테스트용으로 풀어놓음
            "/"
    };

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if (whiteListCheck(httpServletRequest.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        if (!isContainToken(httpServletRequest)) {
            sendJwtExceptionResponse(response, new MalformedJwtException(""));
            return;
        }

        try {
            String token = getToken(httpServletRequest);
            Claims claims = jwtProvider.getClaims(token);
            request.setAttribute(MEMBER_ID,
                    claims.get(MEMBER_ID));
            chain.doFilter(request, response);
        } catch (RuntimeException e) {
            sendJwtExceptionResponse(response, e);
        }
    }

    private boolean whiteListCheck(String uri) {
        return PatternMatchUtils.simpleMatch(whiteListUris, uri);
    }

    private boolean isContainToken(HttpServletRequest request) {
        String authorization = request.getHeader(HEADER_AUTHORIZATION);
        return authorization != null && authorization.startsWith(TOKEN_PREFIX);
    }

    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader(HEADER_AUTHORIZATION);
        return authorization.substring(7).replace("\"", "");
    }

    private void sendJwtExceptionResponse(ServletResponse response, RuntimeException e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());

        response.getWriter().write(
                objectMapper.writeValueAsString(
                        generateErrorApiResponse(e))
        );
    }

    private JwtExceptionResponse generateErrorApiResponse(RuntimeException e) {
        JwtExceptionType jwtExceptionType = JwtExceptionType.from(e);
        return new JwtExceptionResponse(jwtExceptionType);
    }

}
