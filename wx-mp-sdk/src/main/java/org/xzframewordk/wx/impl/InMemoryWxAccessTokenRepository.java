package org.xzframewordk.wx.impl;

import org.xzframewordk.wx.domain.WxAccessToken;
import org.xzframewordk.wx.WxAccessTokenRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryWxAccessTokenRepository implements WxAccessTokenRepository {

    private final Map<String, WxAccessToken> storage = new ConcurrentHashMap<>();

    @Override
    public WxAccessToken save(String appid, WxAccessToken accessToken) {
        storage.put(appid, accessToken);
        return accessToken;
    }

    @Override
    public Optional<WxAccessToken> findByAppid(String appid) {
        return Optional.ofNullable(storage.get(appid));
    }
}
