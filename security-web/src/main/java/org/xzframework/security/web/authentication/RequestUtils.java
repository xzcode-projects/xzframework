package org.xzframework.security.web.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Set;

public abstract class RequestUtils {

    private static final RequestMatcher jsonRequestMatcher;

    static {
        MediaTypeRequestMatcher c = new MediaTypeRequestMatcher(
                MediaType.APPLICATION_JSON,
                MediaType.TEXT_PLAIN
        );
        c.setIgnoredMediaTypes(Set.of(MediaType.ALL));
        jsonRequestMatcher = c;
    }

    public static boolean isJsonRequest(final HttpServletRequest request) {
        return jsonRequestMatcher.matches(request);
    }
}
