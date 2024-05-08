package org.xzframewordk.wx.mp.service.impl;

import org.xzframewordk.wx.ObjectSerializer;
import org.xzframewordk.wx.WxAccessTokenService;
import org.xzframewordk.wx.WxRequestExecutor;
import org.xzframewordk.wx.domain.WxAccessToken;
import org.xzframewordk.wx.mp.domain.QrCodeRequest;
import org.xzframewordk.wx.mp.domain.UnlimitedQRCodeRequest;
import org.xzframewordk.wx.mp.service.MpQrCodeService;

import java.util.Collections;
import java.util.Map;

public class DefaultMpQrCodeService implements MpQrCodeService {

    private final WxAccessTokenService accessTokenService;
    private final WxRequestExecutor requestExecutor;
    private final ObjectSerializer serializer;

    public DefaultMpQrCodeService(WxAccessTokenService accessTokenService, WxRequestExecutor requestExecutor, ObjectSerializer serializer) {
        this.accessTokenService = accessTokenService;
        this.requestExecutor = requestExecutor;
        this.serializer = serializer;
    }

    @Override
    public byte[] getQRCode(String appid, QrCodeRequest request) {
        WxAccessToken accessToken = accessTokenService.getAccessToken(appid);
        return requestExecutor.postForByte(
                "https://api.weixin.qq.com/wxa/getwxacode?access_token={access_token}",
                Collections.emptyMap(),
                Map.of("access_token", accessToken.getAccessToken()),
                serializer.serialize(request)
        );
    }

    @Override
    public byte[] getUnlimitedQRCode(String appid, UnlimitedQRCodeRequest request) {
        WxAccessToken accessToken = accessTokenService.getAccessToken(appid);
        return requestExecutor.postForByte(
                "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token={access_token}",
                Collections.emptyMap(),
                Map.of("access_token", accessToken.getAccessToken()),
                serializer.serialize(request)
        );
    }
}
