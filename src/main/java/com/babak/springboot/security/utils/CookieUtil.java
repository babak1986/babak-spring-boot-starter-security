package com.babak.springboot.security.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import java.util.Arrays;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
public final class CookieUtil {

    private final static int MAX_AGE = 60 * 60 * 24 * 90;
    public final static String JWT_TOKEN_NAME = "b_token";

    public static String get(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(name))
                    .findFirst().orElse(null).getValue();
        }
        return null;
    }

    public static void create(HttpServletResponse response, String name, String value,
                              boolean secure, boolean httpOnly, int maxAge) {
        String cookie = ResponseCookie
                .from(name)
                .value(value)
                .secure(secure)
                .httpOnly(httpOnly)
                .maxAge(maxAge)
                .path("/")
                .build()
                .toString();
        response.addHeader(HttpHeaders.COOKIE, cookie);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie);
    }

    public static void token(HttpServletResponse response, String token) {
        create(response, JWT_TOKEN_NAME, token, false, true, MAX_AGE);
    }

    public static void invalidate(HttpServletResponse response, String name) {
        String cookie = ResponseCookie
                .from(name)
                .maxAge(0)
                .build()
                .toString();
        response.addHeader(HttpHeaders.COOKIE, cookie);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie);
    }
}
