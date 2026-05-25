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
 * 该拦截器会在HTTP请求发送前记录请求URL、请求体、响应状态和响应体。<br/>
 * 该拦截器仅在DEBUG级别下记录日志，以避免生产环境的性能影响。<br/>
 * 此拦截器不会修改请求或响应的内容。输出也不会过滤敏感信息，<br/>
 * 例如密码、个人身份信息等。所以只能用于测试，生产环境禁止使用。
 */
public class RestClientLogInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RestClientLogInterceptor.class);

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {
        if (log.isDebugEnabled()) {
            Long start = System.currentTimeMillis();
            String requestId = UUID.randomUUID().toString();
            String bodyString = new String(body, StandardCharsets.UTF_8);
            log.debug("请求ID:[{}],\r\n,请求URL:[{}],\r\n请求体[{}]", requestId, request.getURI(), bodyString);
            ClientHttpResponse response = execution.execute(request, body);
            byte[] responseBody;
            try (InputStream bodyStream = response.getBody()) {
                responseBody = bodyStream.readAllBytes();
            } catch (Exception e) {
                log.warn("Failed to read response body", e);
                // 继续执行，不影响主流程
                return response;
            }
            log.debug(
                    "响应ID:[{}],\r\n,请求URL: [{}],\r\n 请求体: [{}],\r\n 响应状态: [{}],\r\n 响应体: [{}], 耗时: [{}ms]",
                    requestId,
                    request.getURI(),
                    bodyString,
                    response.getStatusCode(),
                    new String(responseBody, StandardCharsets.UTF_8),
                    System.currentTimeMillis() - start
            );

            return new ClientHttpResponse() {

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
            };
        } else {
            return execution.execute(request, body);
        }
    }

}
