package org.xzframewordk.wx.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xzframewordk.wx.WxAccessTokenRepository;
import org.xzframewordk.wx.WxAccessTokenService;
import org.xzframewordk.wx.WxAppRepository;
import org.xzframewordk.wx.WxRequestExecutor;
import org.xzframewordk.wx.domain.WxAccessToken;
import org.xzframewordk.wx.domain.WxApp;

import java.util.Collections;
import java.util.Map;

public class DefaultWxAccessTokenService implements WxAccessTokenService {

    private final static Logger log = LoggerFactory.getLogger(DefaultWxAccessTokenService.class);

    private final WxRequestExecutor requestExecutor;

    private final WxAccessTokenRepository accessTokenRepository;

    private final WxAppRepository wxAppRepository;

    public DefaultWxAccessTokenService(
            WxRequestExecutor requestExecutor,
            WxAccessTokenRepository accessTokenStorage,
            WxAppRepository wxAppRepository
    ) {
        this.accessTokenRepository = accessTokenStorage;
        this.wxAppRepository = wxAppRepository;
        this.requestExecutor = requestExecutor;
    }

    @Override
    public WxAccessToken getAccessToken(String appid) {
        return accessTokenRepository.computeIfAbsent(appid, this::newAccessTokenIfNecessary);
    }

    private WxAccessToken newAccessTokenIfNecessary(final String appid) {
        WxApp app = wxAppRepository.findByAppid(appid).orElseThrow(() -> new RuntimeException("appid [" + appid + "] not found"));
        return requestExecutor.get(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}",
                Collections.emptyMap(),
                Map.of("appid", appid, "secret", app.getSecret()),
                WxAccessToken.class
        );
    }

}
