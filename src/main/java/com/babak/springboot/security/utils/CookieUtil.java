package com.babak.springboot.security.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
public final class CookieUtil {

    private final static int MAX_AGE = 60 * 60 * 24 * 90;

    public static String get(HttpServletRequest request, String name) {
        return Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(name))
                .findFirst().orElse(null).getValue();
    }

    public static void put(HttpServletResponse response, String name, String value,
                           boolean secure, boolean httpOnly, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(secure);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void token(HttpServletResponse response, String token) {
        put(response, "b_token", token, true, true, CookieUtil.MAX_AGE);
    }

    public static void invalidate(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
