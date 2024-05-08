package org.xzframewordk.wx.oa.service;

import org.xzframewordk.wx.oa.domain.WxMpOAuth2AccessToken;

public interface WxMpOAuth2Service {
    WxMpOAuth2AccessToken getAccessToken(String code);
}
