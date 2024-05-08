package org.xzframewordk.wx

import org.xzframewordk.wx.domain.WxApp

fun WxAppRepository.findByAppidOrNull(appid: String): WxApp? {
    return findByAppid(appid).orElse(null)
}
