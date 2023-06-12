package org.xzframewordk.wx.mp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xzframewordk.wx.mp.domain.MessageResult;
import org.xzframewordk.wx.mp.domain.TemplateMessage;
import org.xzframewordk.wx.mp.domain.WxMpAccessToken;
import org.xzframewordk.wx.mp.service.WxMpAccessTokenService;
import org.xzframewordk.wx.mp.service.WxMpMessageService;
import org.xzframewordk.wx.mp.service.WxRequestExecutor;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class DefaultWxMpMessageService implements WxMpMessageService {
    private final static Logger log = LoggerFactory.getLogger(DefaultWxMpMessageService.class);

    private static final String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={accessToken}";
    private final WxRequestExecutor requestExecutor;

    private final WxMpAccessTokenService accessTokenService;


    public DefaultWxMpMessageService(
            WxRequestExecutor requestExecutor,
            WxMpAccessTokenService accessTokenService
    ) {
        this.requestExecutor = requestExecutor;
        this.accessTokenService = accessTokenService;
    }

    @Override
    public MessageResult sendTemplateMessage(TemplateMessage message) {
        WxMpAccessToken accessToken = accessTokenService.getAccessToken();
        MessageResult result = requestExecutor.post(
                url,
                Collections.emptyMap(),
                Map.of("accessToken", accessToken.getAccessToken()),
                message,
                MessageResult.class
        );
        if (Objects.equals(0, result.errcode())) {
            return result;
        } else {
            log.error("发送模板消息时发生错误，错误码：【{}】,错误消息【{}】", result.errcode(), result.errmsg());
            throw new RuntimeException("发送模板消息时发生错误，错误码：【" + result.errcode() + "】,错误消息【" + result.errmsg() + "】");
        }
    }
}
