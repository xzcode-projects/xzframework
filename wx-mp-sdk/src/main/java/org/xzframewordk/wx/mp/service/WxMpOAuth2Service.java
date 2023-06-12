package org.xzframewordk.wx.mp.service;

import org.xzframewordk.wx.mp.domain.WxMpOAuth2AccessToken;

public interface WxMpOAuth2Service {
    WxMpOAuth2AccessToken getAccessToken(String code);
}
