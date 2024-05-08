package org.xzframewordk.wx.oa.service;

import org.xzframewordk.wx.oa.domain.MessageResult;
import org.xzframewordk.wx.oa.domain.TemplateMessage;

public interface WxMpMessageService {
    MessageResult sendTemplateMessage(String appid, TemplateMessage message);
}
