package org.xzframework.security.config.annotation.web

import org.springframework.http.HttpMethod
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.config.annotation.web.AuthorizeHttpRequestsDsl
import org.springframework.security.web.access.intercept.RequestAuthorizationContext

fun AuthorizeHttpRequestsDsl.authorize(
    method: HttpMethod,
    antPatterns: Array<String>,
    access: AuthorizationManager<RequestAuthorizationContext>
) {
    antPatterns.forEach {
        authorize(method, it, access)
    }
}

fun AuthorizeHttpRequestsDsl.authorize(
    antPatterns: Array<String>,
    access: AuthorizationManager<RequestAuthorizationContext>
) {
    antPatterns.forEach {
        authorize(it, access)
    }
}
