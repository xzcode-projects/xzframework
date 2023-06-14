package org.xzframework.security.verification.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;
import org.xzframework.security.verification.DeviceVerificationCodeService;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DeviceVerificationCodeFilter extends GenericFilterBean {

    public static final String DEVICE_VERIFICATION_CODE_KEY_REQUEST_ATTRIBUTE_NAME = "DeviceVerificationCode.key";
    private final List<RequestMatcher> requestMatchers = new ArrayList<>();
    private final DeviceVerificationCodeService codeService;
    private String verifyIdHeaderName = "verify-id";

    private String verifyCodeHeaderName = "verify-code";

    private String verifyCodeKeyHeaderName = "device-key";

    public DeviceVerificationCodeFilter(DeviceVerificationCodeService codeService) {
        this.codeService = codeService;
    }

    public void setVerifyIdHeaderName(String verifyIdHeaderName) {
        this.verifyIdHeaderName = verifyIdHeaderName;
    }

    public void setVerifyCodeHeaderName(String verifyCodeHeaderName) {
        this.verifyCodeHeaderName = verifyCodeHeaderName;
    }

    public void setVerifyCodeKeyHeaderName(String verifyCodeKeyHeaderName) {
        this.verifyCodeKeyHeaderName = verifyCodeKeyHeaderName;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse rsp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rsp;
        if (requiresValidation(request)) {
            String verifyId = request.getHeader(verifyIdHeaderName);
            String verifyCode = request.getHeader(verifyCodeHeaderName);
            String key = request.getHeader(verifyCodeKeyHeaderName);
            if (StringUtils.isNotBlank(verifyId) &&
                    StringUtils.isNotBlank(key) &&
                    StringUtils.isNotBlank(verifyCode) &&
                    codeService.validate(verifyId, key, verifyCode)) {
                request.setAttribute(DEVICE_VERIFICATION_CODE_KEY_REQUEST_ATTRIBUTE_NAME, key);
                chain.doFilter(req, rsp);
            } else {
                response.setStatus(403);
                response.setContentType("application/json;charset=UTF-8");
                String str = """
                        {
                            "path":"%s",
                            "error":"%s",
                            "message":"%s",
                            "status":%d,                      
                            "timestamp":"%s"
                        }                        
                        """.formatted(request.getRequestURI(),
                        HttpStatus.FORBIDDEN.getReasonPhrase(),
                        "验证码输入错误",
                        HttpStatus.FORBIDDEN.value(),
                        ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                );
                // 给出错误的提示信息
                response.getWriter().println(str);
            }
        } else {
            chain.doFilter(req, rsp);
        }
    }

    public void requestMatcher(RequestMatcher requestMatcher) {
        this.requestMatchers.add(requestMatcher);
    }

    private boolean requiresValidation(HttpServletRequest request) {
        for (RequestMatcher matcher : requestMatchers) {
            if (matcher.matches(request)) {
                return true;
            }
        }
        return false;
    }
}

