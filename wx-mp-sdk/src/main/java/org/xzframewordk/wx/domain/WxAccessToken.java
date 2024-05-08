package org.xzframewordk.wx.domain;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = JacksonBaseWxMpAccessToken.class)
public interface WxAccessToken {
    String getAccessToken();

    ZonedDateTime getExpiresAt();
}

class JacksonBaseWxMpAccessToken implements WxAccessToken, Serializable {

    @Serial
    private static final long serialVersionUID = 4363693927392313909L;

    private final String accessToken;
    private final ZonedDateTime expiresAt;


    public JacksonBaseWxMpAccessToken(
            @JsonSetter("access_token") String accessToken,
            @JsonSetter("expires_in") Long expiresIn
    ) {
        this.accessToken = accessToken;
        this.expiresAt = ZonedDateTime.now().plusSeconds(expiresIn);
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }
}


