package org.xzframework.security.web.wx.mp.oauth2.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.xzframewordk.wx.oa.domain.WxMpOAuth2AccessToken;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

public class WxAuthenticationToken extends AbstractAuthenticationToken implements Serializable {

    @Serial
    private static final long serialVersionUID = -8573831337149756905L;
    private final UserDetails userDetails;
    private final WxMpOAuth2AccessToken accessToken;

    public WxAuthenticationToken(
            final WxMpOAuth2AccessToken accessToken,
            final UserDetails userDetails,
            final Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userDetails = userDetails;
        this.accessToken = accessToken;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return "N/A";
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    public WxMpOAuth2AccessToken getAccessToken() {
        return this.accessToken;
    }

}

