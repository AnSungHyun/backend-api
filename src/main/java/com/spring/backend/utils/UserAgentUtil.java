package com.spring.backend.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserAgentUtil {

    private static final String[] MOBILE_KEYWORDS = {
            "Android", "iPhone", "iPad", "Windows Phone",
            "webOS", "BlackBerry"
    };

    private static final String[] APP_KEYWORDS = {
            "YourAppName", // 앱의 User-Agent 식별자
            "CustomApp"    // 필요한 경우 추가
    };

    private UserAgentUtil() {
        // private 생성자로 인스턴스화 방지
    }

    public static String getDeviceType() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String userAgent = request.getHeader("User-Agent");
        return getDeviceTypeFromUserAgent(userAgent);
    }

    public static String getDeviceTypeFromUserAgent(String userAgent) {
        if (userAgent == null) {
            return "unknown";
        }

        // 앱 체크
        for (String keyword : APP_KEYWORDS) {
            if (userAgent.contains(keyword)) {
                return "app";
            }
        }

        // 모바일 체크
        for (String keyword : MOBILE_KEYWORDS) {
            if (userAgent.contains(keyword)) {
                return "mobile";
            }
        }

        // 나머지는 PC로 간주
        return "pc";
    }
}