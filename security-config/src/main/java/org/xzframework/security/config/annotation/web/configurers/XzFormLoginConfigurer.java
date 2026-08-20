package org.xzframework.security.config.annotation.web.configurers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;


public class XzFormLoginConfigurer<B extends HttpSecurityBuilder<B>> extends
        AbstractHttpConfigurer<XzFormLoginConfigurer<B>, B> {

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final AbstractAuthenticationProcessingFilter authFilter;
    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;

    public XzFormLoginConfigurer(
            AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler,
            AbstractAuthenticationProcessingFilter authFilter
    ) {
        this(successHandler, failureHandler, authFilter, null);
    }


    public XzFormLoginConfigurer(
            AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler,
            AbstractAuthenticationProcessingFilter authFilter,
            AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource
    ) {
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.authFilter = authFilter;
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    @Override
    public void configure(B http) {
        // PortMapper portMapper = http.getSharedObject(PortMapper.class);
        // if (portMapper != null) {
        //     this.authenticationEntryPoint.setPortMapper(portMapper);
        // }
        // RequestCache requestCache = http.getSharedObject(RequestCache.class);
        // if (requestCache != null) {
        //     this.defaultSuccessHandler.setRequestCache(requestCache);
        // }
        this.authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        this.authFilter.setAuthenticationSuccessHandler(this.successHandler);
        this.authFilter.setAuthenticationFailureHandler(this.failureHandler);
        if (this.authenticationDetailsSource != null) {
            this.authFilter.setAuthenticationDetailsSource(this.authenticationDetailsSource);
        }
        SessionAuthenticationStrategy sessionAuthenticationStrategy = http
                .getSharedObject(SessionAuthenticationStrategy.class);
        if (sessionAuthenticationStrategy != null) {
            this.authFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
        }
        RememberMeServices rememberMeServices = http.getSharedObject(RememberMeServices.class);
        if (rememberMeServices != null) {
            this.authFilter.setRememberMeServices(rememberMeServices);
        }
        SecurityContextRepository securityContextRepository = getSecurityContextRepository();
        authFilter.setSecurityContextRepository(securityContextRepository);
        authFilter.setSecurityContextHolderStrategy(getSecurityContextHolderStrategy());
        AbstractAuthenticationProcessingFilter filter = postProcess(this.authFilter);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }


    private SecurityContextRepository getSecurityContextRepository() {
        SecurityContextRepository securityContextRepository = getBuilder().getSharedObject(SecurityContextRepository.class);
        if (securityContextRepository == null) {
            securityContextRepository = new DelegatingSecurityContextRepository(
                    new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository());
        }
        return securityContextRepository;
    }


}