package org.xzframewordk.wx.mp.domain;

import java.io.Serial;
import java.io.Serializable;

public interface TemplateMessageData extends Serializable {

    static TemplateMessageData newData(String value) {
        return new DefaultTemplateMessageData(value);
    }

    String value();
}

record DefaultTemplateMessageData(String value) implements TemplateMessageData {

    @Serial
    private static final long serialVersionUID = -7301174986713137445L;
}
