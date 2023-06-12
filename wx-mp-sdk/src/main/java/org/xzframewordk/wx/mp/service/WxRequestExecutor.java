package org.xzframewordk.wx.mp.service;

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
     * 执行一个请求
     *
     * @param <B>          请求体类型
     * @param <R>          返回的实体类型
     * @param url
     * @param method
     * @param uriVariables
     * @param body
     */
    <B, R> R execute(
            String url,
            String method,
            Map<String, String> headers,
            Map<String, String> uriVariables,
            B body,
            Class<R> valueType
    );
}
