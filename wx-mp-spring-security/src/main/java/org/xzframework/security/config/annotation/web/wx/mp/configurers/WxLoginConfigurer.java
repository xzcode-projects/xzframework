package org.xzframework.security.config.annotation.web.wx.mp.configurers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.xzframewordk.wx.mp.service.WxMpOAuth2Service;
import org.xzframework.security.web.wx.mp.oauth2.authentication.WxCodeAuthenticationProcessingFilter;
import org.xzframework.security.web.wx.mp.oauth2.authentication.WxOauth2AccessTokenAuthenticationToken;
import org.xzframework.security.web.wx.mp.oauth2.authentication.WxOauth2AuthenticationProvider;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class WxLoginConfigurer<H extends HttpSecurityBuilder<H>> extends
        AbstractAuthenticationFilterConfigurer<H, WxLoginConfigurer<H>, WxCodeAuthenticationProcessingFilter> {

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;
    private AuthenticationUserDetailsService<WxOauth2AccessTokenAuthenticationToken> authenticationUserDetailsService;
    private Supplier<String> appidResolver;

    private Function<HttpServletRequest, String> redirectUrlBuilder;


    public WxLoginConfigurer() {
        super(new WxCodeAuthenticationProcessingFilter(), "/login/wx/code");
    }


    public WxLoginConfigurer<H> appidResolver(Supplier<String> appidResolver) {
        this.appidResolver = appidResolver;
        return this;
    }

    public WxLoginConfigurer<H> redirectUrlBuilder(Function<HttpServletRequest, String> redirectUrlBuilder) {
        this.redirectUrlBuilder = redirectUrlBuilder;
        return this;
    }

    public WxLoginConfigurer<H> authenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        super.successHandler(authenticationSuccessHandler);
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        return this;
    }

    public WxLoginConfigurer<H> authenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        super.failureHandler(authenticationFailureHandler);
        this.authenticationFailureHandler = authenticationFailureHandler;
        return this;
    }


    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl);
    }


    @Override
    public void init(H http) {
        WxCodeAuthenticationProcessingFilter authFilter = getAuthenticationFilter();
        authFilter.setAppidResolver(appidResolver);
        if (Objects.nonNull(redirectUrlBuilder)) {
            authFilter.setRedirectUrlBuilder(redirectUrlBuilder);
        }
    }

    @Override
    public void configure(H http) throws Exception {
        ApplicationContext context = http.getSharedObject(ApplicationContext.class);
        WxMpOAuth2Service wxOAuth2Service = context.getBean(WxMpOAuth2Service.class);
        WxOauth2AuthenticationProvider authenticationProvider;
        if (Objects.nonNull(authenticationUserDetailsService)) {
            authenticationProvider = new WxOauth2AuthenticationProvider(
                    wxOAuth2Service,
                    postProcess(authenticationUserDetailsService)
            );
        } else {
            authenticationProvider = new WxOauth2AuthenticationProvider(wxOAuth2Service, null);
        }
        http.authenticationProvider(postProcess(authenticationProvider));
        WxCodeAuthenticationProcessingFilter authFilter = getAuthenticationFilter();
        authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        if (Objects.nonNull(authenticationSuccessHandler)) {
            authFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        }
        if (Objects.nonNull(authenticationFailureHandler)) {
            authFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        }
        SessionAuthenticationStrategy sessionAuthenticationStrategy = http.getSharedObject(SessionAuthenticationStrategy.class);
        if (Objects.nonNull(sessionAuthenticationStrategy)) {
            authFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
        }
        SecurityContextRepository securityContextRepository = http.getSharedObject(SecurityContextRepository.class);
        authFilter.setSecurityContextRepository(securityContextRepository);
        authFilter.setSecurityContextHolderStrategy(getSecurityContextHolderStrategy());
        http.addFilterBefore(postProcess(authFilter), UsernamePasswordAuthenticationFilter.class);
    }
}
