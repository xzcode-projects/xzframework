package org.xzframewordk.wx;

import org.xzframewordk.wx.domain.WxAccessToken;

public interface WxAccessTokenService {

    WxAccessToken getAccessToken(String appid);

}
