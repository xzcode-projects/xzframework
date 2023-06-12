package org.xzframework.security.web.wx.mp.oauth2.authentication;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;

public class WxOAuth2CodeAuthentication implements Authentication {

    @Serial
    private static final long serialVersionUID = 4815555761809821333L;

    private final String code;

    public WxOAuth2CodeAuthentication(String code) {
        super();
        this.code = code;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return code;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new NotImplementedException("the authenticated can not to be set");
    }

}
