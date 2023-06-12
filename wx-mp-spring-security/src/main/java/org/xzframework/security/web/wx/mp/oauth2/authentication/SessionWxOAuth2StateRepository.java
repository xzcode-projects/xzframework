package org.xzframework.security.web.wx.mp.oauth2.authentication;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;

public class SessionWxOAuth2StateRepository implements WxOAuth2StateRepository {

    public static final String WX_LOGIN_CODE_STATE = "wx_login_code_state";

    @Override
    public String get(HttpServletRequest request) {
        Object state = request.getSession().getAttribute(WX_LOGIN_CODE_STATE);
        if (Objects.nonNull(state)) {
            return state.toString();
        } else {
            return null;
        }
    }

    @Override
    public void save(HttpServletRequest request, String state) {
        request.getSession().setAttribute(WX_LOGIN_CODE_STATE, state);
    }
}
