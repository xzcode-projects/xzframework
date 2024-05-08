package org.xzframework.security.web.wx.mp.oauth2.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.util.Assert;
import org.xzframewordk.wx.oa.domain.WxMpOAuth2AccessToken;
import org.xzframewordk.wx.oa.service.WxMpOAuth2Service;

import java.util.Collections;

public class WxOauth2AuthenticationProvider implements AuthenticationProvider,
        InitializingBean, MessageSourceAware {

    private final Logger logger = LoggerFactory.getLogger(WxOauth2AuthenticationProvider.class);
    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();
    private final AuthenticationUserDetailsService<WxOauth2AccessTokenAuthenticationToken> authenticationUserDetailsService;
    private final WxMpOAuth2Service wxOAuth2Service;
    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public WxOauth2AuthenticationProvider(
            WxMpOAuth2Service wxOAuth2Service,
            AuthenticationUserDetailsService<WxOauth2AccessTokenAuthenticationToken> authenticationUserDetailsService
    ) {
        this.authenticationUserDetailsService = authenticationUserDetailsService;
        this.wxOAuth2Service = wxOAuth2Service;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.wxOAuth2Service, "the wxOauth2Service can not be null");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }
        try {
            final String code = String.valueOf(authentication.getCredentials());
            final WxMpOAuth2AccessToken token = wxOAuth2Service.getAccessToken(code);
            final WxOauth2AccessTokenAuthenticationToken authenticationToken = new WxOauth2AccessTokenAuthenticationToken(token, Collections.singleton(new SimpleGrantedAuthority("ROLE_WX_USER")));
            if (authenticationUserDetailsService != null) {
                UserDetails userDetails = authenticationUserDetailsService.loadUserDetails(authenticationToken);
                preAuthenticationChecks.check(userDetails);
                return new WxAuthenticationToken(token, userDetails, authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));
            }
            return authenticationToken;
        } catch (Exception e) {
            throw new BadCredentialsException("微信登录时发生错误", e);
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return WxOAuth2CodeAuthentication.class.isAssignableFrom(authentication);
    }


    private class DefaultPreAuthenticationChecks implements UserDetailsChecker {
        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                logger.debug("User account is locked");

                throw new LockedException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.locked",
                        "User account is locked"));
            }

            if (!user.isEnabled()) {
                logger.debug("User account is disabled");

                throw new DisabledException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.disabled",
                        "User is disabled"));
            }

            if (!user.isAccountNonExpired()) {
                logger.debug("User account is expired");

                throw new AccountExpiredException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.expired",
                        "User account has expired"));
            }
        }
    }
}

