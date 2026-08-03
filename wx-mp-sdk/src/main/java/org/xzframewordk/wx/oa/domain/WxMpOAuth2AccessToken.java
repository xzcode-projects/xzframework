package org.xzframewordk.wx.oa.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * "access_token":"ACCESS_TOKEN",
 * "expires_in":7200,
 * "refresh_token":"REFRESH_TOKEN",
 * "openid":"OPENID",
 * "scope":"SCOPE",
 * "is_snapshotuser": 1,
 * "unionid": "UNIONID"
 */

@JsonDeserialize(as = JacksonBaseWxMpOAuth2AccessToken.class)
public interface WxMpOAuth2AccessToken {

    String accessToken();

    ZonedDateTime expiresAt();

    String refreshToken();

    String openid();

    String scope();

    boolean isSnapshotuser();

    String unionid();

}

record JacksonBaseWxMpOAuth2AccessToken(
        String accessToken,
        ZonedDateTime expiresAt,
        String refreshToken,
        String openid,
        String scope,
        boolean isSnapshotuser,
        String unionid
) implements WxMpOAuth2AccessToken, Serializable {

    @Serial
    private static final long serialVersionUID = 6353018887890523094L;

    @JsonCreator
    JacksonBaseWxMpOAuth2AccessToken(
            @JsonSetter("access_token") String accessToken,
            @JsonSetter("expires_in") Long expiresAt,
            @JsonSetter("refresh_token") String refreshToken,
            @JsonSetter("openid") String openid,
            @JsonSetter("scope") String scope,
            @JsonSetter("is_snapshotuser") Integer isSnapshotuser,
            @JsonSetter("unionid") String unionid
    ) {
        this(accessToken,
                ZonedDateTime.now().plusSeconds(expiresAt),
                refreshToken,
                openid,
                scope,
                Objects.equals(isSnapshotuser, 0),
                unionid
        );
    }

}
