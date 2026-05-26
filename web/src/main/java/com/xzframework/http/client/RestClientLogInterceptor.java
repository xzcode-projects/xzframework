package com.xzframework.http.client;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 日志拦截器，用于记录HTTP请求和响应的详细信息。<br/>
 * 正常情况下仅在DEBUG级别下记录请求和响应日志，以避免生产环境的性能影响。<br/>
 * 当响应状态码为4xx/5xx、读取响应体失败或请求执行异常（如超时）时，<br/>
 * 会以WARN/ERROR级别强制记录请求体，以便在生产环境中排查问题。<br/>
 * 此拦截器不会修改请求或响应的内容。输出不会过滤敏感信息，<br/>
 * 例如密码、个人身份信息等，请谨慎使用。
 */
public class RestClientLogInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RestClientLogInterceptor.class);

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {
        long start = System.currentTimeMillis();
        String requestId = UUID.randomUUID().toString();

        // 记录请求日志
        if (log.isDebugEnabled()) {
            log.debug("请求ID:[{}], 请求URL:[{}], 请求体:[{}]",
                    requestId, request.getURI(), new String(body, StandardCharsets.UTF_8));
        }

        // 执行请求
        ClientHttpResponse response;
        try {
            response = execution.execute(request, body);
        } catch (IOException e) {
            log.error("请求执行失败, 请求ID:[{}], 请求URL:[{}], 请求体:[{}], 耗时:[{}ms]",
                    requestId, request.getURI(), new String(body, StandardCharsets.UTF_8),
                    System.currentTimeMillis() - start, e);
            throw e;
        }

        // 仅在需要记录日志时才读取响应体，避免不必要的性能开销
        HttpStatusCode statusCode = response.getStatusCode();
        if (!statusCode.isError() && !log.isDebugEnabled()) {
            return response;
        } else {
            String bodyString = new String(body, StandardCharsets.UTF_8);
            byte[] responseBody;
            try (InputStream bodyStream = response.getBody()) {
                responseBody = bodyStream.readAllBytes();
            } catch (Exception e) {
                log.warn("读取响应体失败, 请求ID:[{}], 请求URL:[{}], 请求体:[{}], 响应状态:[{}], 耗时:[{}ms]",
                        requestId, request.getURI(), bodyString, statusCode,
                        System.currentTimeMillis() - start, e);
                return response;
            }

            // 记录响应日志
            long timeTaken = System.currentTimeMillis() - start;
            String responseBodyString = new String(responseBody, StandardCharsets.UTF_8);
            if (statusCode.isError()) {
                log.warn("响应错误, 请求ID:[{}], 请求URL:[{}], 请求体:[{}], 响应状态:[{}], 响应体:[{}], 耗时:[{}ms]",
                        requestId, request.getURI(), bodyString, statusCode, responseBodyString, timeTaken);
            } else {
                log.debug("响应ID:[{}], 请求URL:[{}], 请求体:[{}], 响应状态:[{}], 响应体:[{}], 耗时:[{}ms]",
                        requestId, request.getURI(), bodyString, statusCode, responseBodyString, timeTaken);
            }
            return new ResponseWrapper(response, responseBody);
        }
    }

    private record ResponseWrapper(ClientHttpResponse response, byte[] responseBody) implements ClientHttpResponse {

        @Override
        @NonNull
        public HttpHeaders getHeaders() {
            return response.getHeaders();
        }

        @Override
        @NonNull
        public InputStream getBody() throws IOException {
            return new ByteArrayInputStream(responseBody);
        }

        @Override
        @NonNull
        public HttpStatusCode getStatusCode() throws IOException {
            return response.getStatusCode();
        }

        @Override
        @NonNull
        public String getStatusText() throws IOException {
            return response.getStatusText();
        }

        @Override
        public void close() {
            response.close();
        }

    }

}
