package org.xzframework.security.web;

import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

/**
 * 资源鉴权,检测资源是否能访问
 */
public class XzAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final AuthorityResourceResolver authorityResourceResolver;

    private final ConcurrentMap<WebResource, RequestMatcher> requestCache = new ConcurrentHashMap<>();

    public XzAuthorizationManager(AuthorityResourceResolver authorityResourceResolver) {
        this.authorityResourceResolver = authorityResourceResolver;
    }

    private RequestMatcher generateAntPatchMatcher(String url, HttpMethod method) {
        if (url != null && !url.isBlank() && method != null) {
            return PathPatternRequestMatcher.withDefaults().matcher(method, url);
        }
        if (url != null && !url.isBlank()) {
            return PathPatternRequestMatcher.withDefaults().matcher(url);
        }
        if (method != null) {
            return PathPatternRequestMatcher.withDefaults().matcher(method, "/**");
        }
        return request -> false;
    }

    private RequestMatcher generateRegexPatchMatcher(String url, HttpMethod method) {
        if (url != null && !url.isBlank() && method != null) {
            return new RegexRequestMatcher(url, method.name(), false);
        }
        if (url != null && !url.isBlank()) {
            return RegexRequestMatcher.regexMatcher(url);
        }
        if (method != null) {
            return RegexRequestMatcher.regexMatcher(method);
        }
        return request -> false;
    }

    private RequestMatcher generateRequestMatcher(WebResource request) {
        if (request.match() == MatchType.ANT_PATH) {
            return generateAntPatchMatcher(request.url(), request.method());
        } else {
            return generateRegexPatchMatcher(request.url(), request.method());
        }
    }

    @Override
    public AuthorizationResult authorize(Supplier<? extends Authentication> authentication, RequestAuthorizationContext context) {
        Authentication auth = authentication.get();
        boolean hasAccess = auth != null && auth.getAuthorities().stream()
                .flatMap(authority -> authorityResourceResolver.resolve(authority).stream())
                .map(request -> requestCache.computeIfAbsent(request, this::generateRequestMatcher))
                .anyMatch(matcher -> matcher.matches(context.getRequest()));
        return new AuthorizationDecision(hasAccess);
    }

}
