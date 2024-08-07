package com.babak.springboot.security.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
public final class CookieUtil {

    public static String get(HttpServletRequest request, String name) {
        return Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(name))
                .findFirst().orElse(null).getValue();
    }

    public static void put(HttpServletResponse response, String name, String value, boolean secure, boolean httpOnly) {
        String cookie = String.format("%s=%s; secure=%s; httpOnly=%s", name, value, secure, httpOnly);
        response.addHeader(HttpHeaders.COOKIE, cookie);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie);
    }

    public static void token(HttpServletResponse response, String token) {
        put(response, "b_token", token, true, true);
    }
}
