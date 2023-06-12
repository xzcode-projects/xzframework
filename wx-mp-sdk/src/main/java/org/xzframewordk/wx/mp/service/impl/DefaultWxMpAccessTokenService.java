package org.xzframewordk.wx.mp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.locks.DefaultLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.xzframewordk.wx.mp.domain.WxMpAccessToken;
import org.xzframewordk.wx.mp.properties.WxMpProperties;
import org.xzframewordk.wx.mp.service.WxMpAccessTokenService;
import org.xzframewordk.wx.mp.service.WxMpAccessTokenStorage;
import org.xzframewordk.wx.mp.service.WxRequestExecutor;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;


public class DefaultWxMpAccessTokenService implements WxMpAccessTokenService {

    private final static Logger log = LoggerFactory.getLogger(DefaultWxMpAccessTokenService.class);

    private final String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}";

    private final WxRequestExecutor requestExecutor;

    private final WxMpAccessTokenStorage accessTokenStorage;

    private final WxMpProperties properties;

    private final LockRegistry lockRegistry;

    private final String lockKeyPrefix;


    public DefaultWxMpAccessTokenService(
            WxRequestExecutor requestExecutor,
            WxMpAccessTokenStorage accessTokenStorage,
            WxMpProperties properties
    ) {
        this(requestExecutor, accessTokenStorage, properties, new DefaultLockRegistry());
    }


    public DefaultWxMpAccessTokenService(
            WxRequestExecutor requestExecutor,
            WxMpAccessTokenStorage accessTokenStorage,
            WxMpProperties properties,
            LockRegistry lockRegistry
    ) {
        this(requestExecutor, accessTokenStorage, properties, lockRegistry, "lock_wx_mp_access_token_");
    }

    public DefaultWxMpAccessTokenService(
            WxRequestExecutor requestExecutor,
            WxMpAccessTokenStorage accessTokenStorage,
            WxMpProperties properties,
            LockRegistry lockRegistry,
            String lockKeyPrefix
    ) {
        this.accessTokenStorage = accessTokenStorage;
        this.properties = properties;
        this.lockRegistry = lockRegistry;
        this.lockKeyPrefix = lockKeyPrefix;
        this.requestExecutor = requestExecutor;
    }

    @Override
    public WxMpAccessToken getAccessToken() {
        final String appid = properties.getAppid();
        return readAccessToken(appid).orElseGet(() -> this.newAccessTokenIfNecessary(appid));
    }

    private Optional<WxMpAccessToken> readAccessToken(String appid) {
        Optional<WxMpAccessToken> read = accessTokenStorage.findByAppid(appid);
        if (read.isPresent()) {
            WxMpAccessToken token = read.get();
            if (ZonedDateTime.now().isBefore(token.getExpiresAt())) {
                return Optional.of(token);
            }
        }
        return Optional.empty();
    }


    private WxMpAccessToken newAccessToken(String appid) {
        WxMpAccessToken token = requestExecutor.get(
                url,
                Collections.emptyMap(),
                Map.of("appid", appid, "secret", properties.getSecret()),
                WxMpAccessToken.class
        );
        return accessTokenStorage.save(appid, token);
    }

    private WxMpAccessToken newAccessTokenIfNecessary(final String appid) {
        Lock lock = null;
        try {
            lock = lockRegistry.obtain(lockKeyPrefix + appid);
            lock.lock();
            return this.readAccessToken(appid).orElseGet(() -> this.newAccessToken(appid));
        } catch (Exception e) {
            log.error("获取access_token时产生异常", e);
            throw new RuntimeException(e);
        } finally {
            if (lock != null) {
                lock.unlock();
            }
        }
    }
}
