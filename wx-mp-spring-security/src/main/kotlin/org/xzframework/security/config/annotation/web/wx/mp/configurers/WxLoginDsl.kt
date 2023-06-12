package org.xzframework.security.config.annotation.web.wx.mp.configurers

import org.springframework.security.config.annotation.web.HttpSecurityDsl
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

class WxLoginDsl {
    var appidResolver: () -> String = { "" }
    var loginProcessingUrl: String? = null
    var authenticationSuccessHandler: AuthenticationSuccessHandler? = null
    var authenticationFailureHandler: AuthenticationFailureHandler? = null
}

fun HttpSecurityDsl.wxLogin(config: WxLoginDsl.() -> Unit) {
    val dsl = WxLoginDsl().apply(config)
    apply(WxLoginConfigurer()) {
        appidResolver(dsl.appidResolver)
        dsl.appidResolver.also { appidResolver(it) }
        dsl.loginProcessingUrl?.also { loginProcessingUrl(it) }
        dsl.authenticationSuccessHandler?.also { authenticationSuccessHandler(it) }
        dsl.authenticationFailureHandler?.also { authenticationFailureHandler(it) }
    }
}


