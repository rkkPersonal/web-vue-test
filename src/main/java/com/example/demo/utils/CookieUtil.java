package com.example.demo.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static final String COOKIE_KEY = "demo-key";


    public static void newCookie(String value) {
        HttpServletResponse response1 = ThreadLocalUtil.get();
        Cookie cookie = new Cookie(COOKIE_KEY, value);
        //cookie.setMaxAge(60);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        response1.addCookie(cookie);
    }


    public static String getCookieValue(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIE_KEY)) {
                    return cookie.getValue();
                }
            }

        }
        return null;
    }
}
