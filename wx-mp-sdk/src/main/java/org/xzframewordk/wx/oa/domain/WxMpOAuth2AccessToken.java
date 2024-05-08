package org.xzframewordk.wx.oa.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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
    String getAccessToken();

    ZonedDateTime getExpiresAt();

    String getRefreshToken();

    String getOpenid();

    String getScope();

    boolean isSnapshotuser();

    String getUnionid();


}

class JacksonBaseWxMpOAuth2AccessToken implements WxMpOAuth2AccessToken, Serializable {

    @Serial
    private static final long serialVersionUID = 6353018887890523094L;

    private final String accessToken;

    private final ZonedDateTime expiresAt;

    private final String refreshToken;

    private final String openid;
    private final String scope;

    private final boolean isSnapshotuser;

    private final String unionid;


    @JsonCreator
    public JacksonBaseWxMpOAuth2AccessToken(
            @JsonSetter("access_token") String accessToken,
            @JsonSetter("expires_in") Long expiresIn,
            @JsonSetter("refresh_token") String refreshToken,
            @JsonSetter("openid") String openid,
            @JsonSetter("scope") String scope,
            @JsonSetter("is_snapshotuser") Integer isSnapshotuser,
            @JsonSetter("unionid") String unionid
    ) {
        this.accessToken = accessToken;
        this.expiresAt = ZonedDateTime.now().plusSeconds(expiresIn);
        this.refreshToken = refreshToken;
        this.openid = openid;
        this.scope = scope;
        this.isSnapshotuser = Objects.equals(isSnapshotuser, 0);
        this.unionid = unionid;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }


    @Override
    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }

    @Override
    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public String getOpenid() {
        return openid;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public boolean isSnapshotuser() {
        return isSnapshotuser;
    }

    @Override
    public String getUnionid() {
        return unionid;
    }
}
