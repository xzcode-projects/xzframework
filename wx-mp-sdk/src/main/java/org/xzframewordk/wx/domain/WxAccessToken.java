package org.xzframewordk.wx.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = JacksonBaseWxMpAccessToken.class)
public interface WxAccessToken {

    String accessToken();

    ZonedDateTime expiresAt();

}

record JacksonBaseWxMpAccessToken(
        String accessToken,
        ZonedDateTime expiresAt
) implements WxAccessToken, Serializable {

    @Serial
    private static final long serialVersionUID = 4363693927392313909L;

    @JsonCreator
    JacksonBaseWxMpAccessToken(
            @JsonSetter("access_token") String accessToken,
            @JsonSetter("expires_in") Long expiresAt
    ) {
        this(accessToken, ZonedDateTime.now().plusSeconds(expiresAt));
    }

}


