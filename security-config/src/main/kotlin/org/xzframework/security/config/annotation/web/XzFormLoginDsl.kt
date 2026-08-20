package org.xzframework.security.config.annotation.web

import org.springframework.security.config.annotation.web.HttpSecurityDsl
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.authentication.*
import org.xzframework.security.config.annotation.web.configurers.XzFormLoginConfigurer

class XzFormLoginDsl {
    var authenticationProcessingFilter: AbstractAuthenticationProcessingFilter = UsernamePasswordAuthenticationFilter()
    var authenticationSuccessHandler: AuthenticationSuccessHandler = ForwardAuthenticationSuccessHandler("/")
    var authenticationFailureHandler: AuthenticationFailureHandler = ForwardAuthenticationFailureHandler("/")
}

fun HttpSecurityDsl.xFormLogin(config: XzFormLoginDsl.() -> Unit) {
    val dsl = XzFormLoginDsl().apply(config)
    val configurer = XzFormLoginConfigurer<HttpSecurity>(
        dsl.authenticationSuccessHandler,
        dsl.authenticationFailureHandler,
        dsl.authenticationProcessingFilter
    )
    this.apply(configurer)
}