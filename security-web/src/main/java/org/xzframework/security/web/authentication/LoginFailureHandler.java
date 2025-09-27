package org.xzframework.security.web.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.xzframework.security.web.authentication.RequestUtils.isJsonRequest;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        if (isJsonRequest(request)) {
            String body = """
                    {
                        "path":"%s",
                        "error":"%s",
                        "message":"%s",
                        "status":%d,
                        "timestamp":"%s"
                    }
                    """.formatted(
                    request.getRequestURI(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                    exception.getMessage(),
                    HttpStatus.UNAUTHORIZED.value(),
                    ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
            );
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(body);
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}
