package org.xzframework.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.session.web.http.HttpSessionIdResolver;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

public class HeaderHttpWithRequestParameterSessionIdResolver implements HttpSessionIdResolver {

    private final HttpSessionIdResolver httpSessionIdResolver;
    private final String parameterName;

    public HeaderHttpWithRequestParameterSessionIdResolver(HttpSessionIdResolver httpSessionIdResolver, String parameterName) {
        this.httpSessionIdResolver = httpSessionIdResolver;
        this.parameterName = parameterName;
    }

    public HeaderHttpWithRequestParameterSessionIdResolver(HttpSessionIdResolver httpSessionIdResolver) {
        this(httpSessionIdResolver, "token");
    }

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        List<String> results = httpSessionIdResolver.resolveSessionIds(request);
        if (results.isEmpty()) {
            String[] tokens = request.getParameterValues(parameterName);
            if (tokens != null) {
                return Arrays.asList(tokens);
            } else {
                return emptyList();
            }
        } else {
            return results;
        }
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        httpSessionIdResolver.setSessionId(request, response, sessionId);
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        httpSessionIdResolver.expireSession(request, response);
    }
}
