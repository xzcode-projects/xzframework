package org.xzframewordk.wx.impl;

import org.xzframewordk.wx.WxAccessTokenRepository;
import org.xzframewordk.wx.domain.WxAccessToken;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class InMemoryWxAccessTokenRepository implements WxAccessTokenRepository {

    private final Map<String, WxAccessToken> storage = new ConcurrentHashMap<>();

    @Override
    public WxAccessToken computeIfAbsent(String appid, Function<String, WxAccessToken> mappingFunction) {
        return storage.computeIfAbsent(appid, mappingFunction);
    }

}
