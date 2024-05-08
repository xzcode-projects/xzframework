package org.xzframewordk.wx;

import java.util.Map;

public interface WxRequestExecutor {

    default <R> R get(String url,
                      Map<String, String> headers,
                      Map<String, String> uriVariables,
                      Class<R> valueType
    ) {
        return execute(url, "GET", headers, uriVariables, null, valueType);
    }

    default <B, R> R post(
            String url,
            Map<String, String> headers,
            Map<String, String> uriVariables,
            B body,
            Class<R> valueType
    ) {
        return execute(url, "POST", headers, uriVariables, body, valueType);
    }


    /**
     * 执行一个请求，并返回响应字节
     *
     * @param url          请求uri
     * @param headers      请求头
     * @param uriVariables 路径参数
     * @param body         请求体
     */
    <B, R> R execute(
            String url,
            String method,
            Map<String, String> headers,
            Map<String, String> uriVariables,
            B body,
            Class<R> valueType
    );

    byte[] postForByte(String url, Map<String, String> headers, Map<String, String> uriVariables, Object body);
}
