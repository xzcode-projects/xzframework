package org.xzframewordk.wx.mp.service;

import org.xzframewordk.wx.mp.domain.MessageResult;
import org.xzframewordk.wx.mp.domain.TemplateMessage;

public interface WxMpMessageService {
    MessageResult sendTemplateMessage(TemplateMessage message);
}
