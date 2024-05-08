package org.xzframewordk.wx.oa.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xzframewordk.wx.oa.domain.MessageResult;
import org.xzframewordk.wx.oa.domain.TemplateMessage;
import org.xzframewordk.wx.domain.WxAccessToken;
import org.xzframewordk.wx.WxAccessTokenService;
import org.xzframewordk.wx.oa.service.WxMpMessageService;
import org.xzframewordk.wx.WxRequestExecutor;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class DefaultWxMpMessageService implements WxMpMessageService {
    private final static Logger log = LoggerFactory.getLogger(DefaultWxMpMessageService.class);

    private static final String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={accessToken}";
    private final WxRequestExecutor requestExecutor;

    private final WxAccessTokenService accessTokenService;


    public DefaultWxMpMessageService(
            WxRequestExecutor requestExecutor,
            WxAccessTokenService accessTokenService
    ) {
        this.requestExecutor = requestExecutor;
        this.accessTokenService = accessTokenService;
    }

    public MessageResult sendTemplateMessage(String appid, TemplateMessage message) {
        WxAccessToken accessToken = accessTokenService.getAccessToken(appid);
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
