package org.xzframewordk.wx;

import org.xzframewordk.wx.domain.WxAccessToken;

import java.util.Optional;

public interface WxAccessTokenRepository {
    WxAccessToken save(String appid, WxAccessToken accessToken);

    Optional<WxAccessToken> findByAppid(String appid);
}
