package org.xzframewordk.wx.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.locks.DefaultLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.xzframewordk.wx.WxAccessTokenRepository;
import org.xzframewordk.wx.WxAccessTokenService;
import org.xzframewordk.wx.WxAppRepository;
import org.xzframewordk.wx.WxRequestExecutor;
import org.xzframewordk.wx.domain.WxAccessToken;
import org.xzframewordk.wx.domain.WxApp;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;


public class DefaultWxAccessTokenService implements WxAccessTokenService {

    private final static Logger log = LoggerFactory.getLogger(DefaultWxAccessTokenService.class);

    private final WxRequestExecutor requestExecutor;

    private final WxAccessTokenRepository accessTokenRepository;

    private final WxAppRepository wxAppRepository;

    private final LockRegistry lockRegistry;

    private final String lockKeyPrefix;


    public DefaultWxAccessTokenService(
            WxRequestExecutor requestExecutor,
            WxAccessTokenRepository accessTokenStorage,
            WxAppRepository wxAppRepository
    ) {
        this(requestExecutor, accessTokenStorage, wxAppRepository, new DefaultLockRegistry());
    }


    public DefaultWxAccessTokenService(
            WxRequestExecutor requestExecutor,
            WxAccessTokenRepository accessTokenStorage,
            WxAppRepository wxAppRepository,
            LockRegistry lockRegistry
    ) {
        this(requestExecutor, accessTokenStorage, wxAppRepository, lockRegistry, "lock_wx_mp_access_token_");
    }

    public DefaultWxAccessTokenService(
            WxRequestExecutor requestExecutor,
            WxAccessTokenRepository accessTokenStorage,
            WxAppRepository wxAppRepository,
            LockRegistry lockRegistry,
            String lockKeyPrefix
    ) {
        this.accessTokenRepository = accessTokenStorage;
        this.wxAppRepository = wxAppRepository;
        this.lockRegistry = lockRegistry;
        this.lockKeyPrefix = lockKeyPrefix;
        this.requestExecutor = requestExecutor;
    }

    @Override
    public WxAccessToken getAccessToken(String appid) {
        return readAccessToken(appid).orElseGet(() -> this.newAccessTokenIfNecessary(appid));
    }

    private Optional<WxAccessToken> readAccessToken(String appid) {
        Optional<WxAccessToken> read = accessTokenRepository.findByAppid(appid);
        if (read.isPresent()) {
            WxAccessToken token = read.get();
            if (ZonedDateTime.now().isBefore(token.getExpiresAt())) {
                return Optional.of(token);
            }
        }
        return Optional.empty();
    }


    private WxAccessToken newAccessTokenIfNecessary(final String appid) {
        Lock lock = null;
        try {
            lock = lockRegistry.obtain(lockKeyPrefix + appid);
            lock.lock();
            return this.readAccessToken(appid).orElseGet(() -> {
                WxApp app = wxAppRepository.findByAppid(appid).orElseThrow(() -> new RuntimeException("appid [" + appid + "] not found"));
                WxAccessToken token = requestExecutor.get(
                        "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}",
                        Collections.emptyMap(),
                        Map.of("appid", appid, "secret", app.getSecret()),
                        WxAccessToken.class
                );
                return accessTokenRepository.save(appid, token);
            });
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
