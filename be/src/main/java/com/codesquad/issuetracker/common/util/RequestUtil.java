package com.codesquad.issuetracker.common.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static String extractAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return authorizationHeader.substring(7);
    }

    public static Long extractMemberId(HttpServletRequest request) {
        return (Long) request.getAttribute("memberId");
    }
}
