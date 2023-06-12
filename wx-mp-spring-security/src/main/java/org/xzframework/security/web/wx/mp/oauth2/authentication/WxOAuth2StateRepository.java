package org.xzframework.security.web.wx.mp.oauth2.authentication;

import jakarta.servlet.http.HttpServletRequest;

public interface WxOAuth2StateRepository {

    String get(HttpServletRequest request);

    void save(HttpServletRequest request, String state);
}
