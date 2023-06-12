package org.xzframework.security.web.wx.mp.oauth2.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.xzframewordk.wx.mp.domain.WxMpOAuth2AccessToken;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;

public class WxOauth2AccessTokenAuthenticationToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = 9069951558338657308L;

    private final WxMpOAuth2AccessToken accessToken;

    public WxOauth2AccessTokenAuthenticationToken(WxMpOAuth2AccessToken accessToken) {
        this(accessToken, Collections.singletonList(new SimpleGrantedAuthority("ROLE_WX_USER")));
    }

    public WxOauth2AccessTokenAuthenticationToken(WxMpOAuth2AccessToken accessToken, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.accessToken = accessToken;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return "N/A";
    }

    @Override
    public Object getPrincipal() {
        return accessToken;
    }
}
