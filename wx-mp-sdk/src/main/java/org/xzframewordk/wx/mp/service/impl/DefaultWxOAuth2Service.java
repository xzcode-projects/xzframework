package org.xzframewordk.wx.mp.service.impl;

import org.xzframewordk.wx.mp.domain.WxMpOAuth2AccessToken;
import org.xzframewordk.wx.mp.properties.WxMpProperties;
import org.xzframewordk.wx.mp.service.WxMpOAuth2Service;
import org.xzframewordk.wx.mp.service.WxRequestExecutor;

import java.util.Collections;
import java.util.Map;

public class DefaultWxOAuth2Service implements WxMpOAuth2Service {

    private static final String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={appid}&secret={secret}&code={code}&grant_type=authorization_code";

    private final WxMpProperties properties;


    private final WxRequestExecutor requestExecutor;

    public DefaultWxOAuth2Service(
            WxMpProperties properties,
            WxRequestExecutor requestExecutor
    ) {
        this.properties = properties;
        this.requestExecutor = requestExecutor;
    }

    @Override
    public WxMpOAuth2AccessToken getAccessToken(String code) {
        return requestExecutor.get(
                url,
                Collections.emptyMap(),
                Map.of("appid", properties.getAppid(), "secret", properties.getSecret(), "code", code),
                WxMpOAuth2AccessToken.class
        );
    }
}
