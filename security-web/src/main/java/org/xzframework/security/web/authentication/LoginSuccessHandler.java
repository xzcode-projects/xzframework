package org.xzframework.security.web.authentication;

import jakarta.servlet.ServletException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.function.Function;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    private final Function<Object, String> principalConverter;

    public LoginSuccessHandler(Function<Object, String> principalConverter) {
        this.principalConverter = principalConverter;
    }

    @Override
    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.getWriter().println(principalConverter.apply(authentication.getPrincipal()));
    }
}
