package org.xzframework.security.web.wx.mp.oauth2.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Supplier;

public class WxCodeAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private final Function<HttpServletRequest, String> redirectUrlBuilder = request -> {
        String scheme = request.getScheme();
        String host = request.getHeader("host");
        return scheme + "://" + host + request.getServletPath();
    };
    private final Supplier<String> appidResolver;

    private final WxOAuth2StateRepository stateRepository = new SessionWxOAuth2StateRepository();

    public WxCodeAuthenticationProcessingFilter(Supplier<String> appidResolver) {
        super("/login/wx/code");
        this.appidResolver = appidResolver;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        if (StringUtils.isBlank(code)) {
            response.sendRedirect(buildRedirect(request));
            return null;
        }
        String savedState = stateRepository.get(request);
        if (!StringUtils.equals(savedState, state)) {
            throw new StateValidationFailureException("the give state not equals to request state");
        }
        return this.getAuthenticationManager().authenticate(new WxOAuth2CodeAuthentication(code));
    }


    private String buildRedirect(HttpServletRequest request) {
        String state = RandomStringUtils.randomAlphabetic(6);
        stateRepository.save(request, state);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://open.weixin.qq.com/connect/oauth2/authorize");
        builder.queryParam("appid", appidResolver.get());
        builder.queryParam("redirect_uri", redirectUrlBuilder.apply(request));
        builder.queryParam("response_type", "code");
        builder.queryParam("scope", "snsapi_base");
        builder.queryParam("state", state);
        builder.fragment("wechat_redirect");
        return builder.toUriString();
    }
}

