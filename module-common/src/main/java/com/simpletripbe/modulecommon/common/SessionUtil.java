package com.dashboardbe.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    private static final String LOGIN_ID = "loginID";

    // 인스턴스화 방지
    private SessionUtil() {}

    /**
     * 로그인한 고객의 아이디를 세션에서 꺼낸다.
     */
    public static String getLoginId(HttpSession session) {
        return (String) session.getAttribute(LOGIN_ID);
    }

    /**
     * 로그인한 고객의 id를 세션에 저장한다.
     */
    public static void setLoginId(HttpSession session, String id) {
        session.setAttribute(LOGIN_ID, id);
    }

}
