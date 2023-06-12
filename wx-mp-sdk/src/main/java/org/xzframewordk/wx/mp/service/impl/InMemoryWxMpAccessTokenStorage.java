package org.xzframewordk.wx.mp.service.impl;

import org.xzframewordk.wx.mp.domain.WxMpAccessToken;
import org.xzframewordk.wx.mp.service.WxMpAccessTokenStorage;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryWxMpAccessTokenStorage implements WxMpAccessTokenStorage {

    private final Map<String, WxMpAccessToken> storage = new ConcurrentHashMap<>();

    @Override
    public WxMpAccessToken save(String appid, WxMpAccessToken accessToken) {
        storage.put(appid, accessToken);
        return accessToken;
    }

    @Override
    public Optional<WxMpAccessToken> findByAppid(String appid) {
        return Optional.ofNullable(storage.get(appid));
    }
}
