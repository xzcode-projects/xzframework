package org.xzframewordk.wx.mp.domain;

import java.io.Serial;
import java.io.Serializable;

public interface TemplateMessageMiniprogram extends Serializable {

    String appid();

    String pagepath();
}

record DefaultTemplateMessageMiniprogram(String appid, String pagepath) implements TemplateMessageMiniprogram {
    @Serial
    private static final long serialVersionUID = -2395758646330319125L;
}
