package org.xzframewordk.wx.mp.service;

import org.xzframewordk.wx.mp.domain.WxMpAccessToken;

import java.util.Optional;

public interface WxMpAccessTokenStorage {
    WxMpAccessToken save(String appid, WxMpAccessToken accessToken);

    Optional<WxMpAccessToken> findByAppid(String appid);
}
