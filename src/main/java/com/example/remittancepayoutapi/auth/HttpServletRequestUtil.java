package com.example.remittancepayoutapi.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

public class HttpServletRequestUtil {

    public static void clearCookie(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String cookieName) {
        Map<String, String> cookieMap = getCookie(httpRequest, cookieName);
        if (cookieMap.isEmpty()) {
            return;
        }
        String cookieToClear = cookieMap.get(cookieName);
        if (!cookieToClear.isEmpty()) {
            Cookie clearCookie = new Cookie(cookieName, null);
            clearCookie.setPath("/");
            clearCookie.setMaxAge(0);
            httpResponse.addCookie(clearCookie);
        }

    }

    public static Map<String, String> getCookie(HttpServletRequest httpRequest, String cookieName) {
        Map<String, String> map = new HashMap<>();
        if (httpRequest.getCookies() != null) {
            Cookie[] cookies = httpRequest.getCookies();

            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    map.put(cookie.getName(), cookie.getValue() + "#" + cookie.getMaxAge());
                    return map;
                }
            }

        }
        return map;
    }

    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        clearCookie(request, response, cookieName);
        response.addCookie(cookie);
    }
}
