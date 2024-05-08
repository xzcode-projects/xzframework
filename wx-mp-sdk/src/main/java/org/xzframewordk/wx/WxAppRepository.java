package org.xzframewordk.wx;

import org.xzframewordk.wx.domain.WxApp;

import java.util.Optional;

public interface WxAppRepository {
    Optional<WxApp> findByAppid(String appid);
}
