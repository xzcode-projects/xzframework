package org.xzframewordk.wx.mp.domain;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serial;
import java.io.Serializable;

@JsonDeserialize(as = DefaultMessageResult.class)
public interface MessageResult extends Serializable {
    Integer errcode();

    String errmsg();

    Long msgid();
}

record DefaultMessageResult(
        @JsonSetter("errcode") Integer errcode,
        @JsonSetter("errmsg") String errmsg,
        @JsonSetter("msgid") Long msgid
) implements MessageResult {
    @Serial
    private static final long serialVersionUID = 6950208640153181553L;

}
