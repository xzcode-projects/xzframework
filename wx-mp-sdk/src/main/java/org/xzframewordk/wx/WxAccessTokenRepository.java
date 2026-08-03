package org.xzframewordk.wx;

import org.xzframewordk.wx.domain.WxAccessToken;

import java.util.function.Function;

public interface WxAccessTokenRepository {

    WxAccessToken computeIfAbsent(String appid, Function<String, WxAccessToken> mappingFunction);

}
