package org.xzframework.security.web.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.xzframework.security.web.authentication.RequestUtils.isJsonRequest;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final Function<Object, String> resultConverter;

    public LoginFailureHandler(Function<Object, String> resultConverter) {
        this.resultConverter = resultConverter;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (isJsonRequest(request)) {
            Map<String, Object> result = new HashMap<>();
            result.put("path", request.getRequestURI());
            result.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            result.put("message", exception.getMessage());
            result.put("status", HttpStatus.UNAUTHORIZED.value());
            result.put("timestamp", ZonedDateTime.now());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(resultConverter.apply(result));
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}
