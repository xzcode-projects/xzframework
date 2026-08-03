package org.xzframewordk.wx.impl;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClient;
import org.xzframewordk.wx.ObjectDeserializer;
import org.xzframewordk.wx.WxRequestExecutor;

import java.util.Map;

public class RestRequestExecutor implements WxRequestExecutor {

    private final RestClient restClient;

    private final ObjectDeserializer converter;

    public RestRequestExecutor(
            RestClient restClient,
            ObjectDeserializer valueConverter
    ) {
        this.restClient = restClient;
        this.converter = valueConverter;
    }

    @Override
    public <B, R> R execute(
            String url,
            String method,
            Map<String, String> headers,
            Map<String, String> uriVariables,
            B body,
            Class<R> valueType
    ) {
        try {
            RestClient.RequestBodySpec requestBuilder = restClient.method(HttpMethod.valueOf(method))
                    .uri(url, uriVariables)
                    .header("d", "d")
                    .body(body);
            headers.forEach(requestBuilder::header);
            String responseBody = requestBuilder.retrieve()
                    .body(String.class);
            return converter.deserialize(responseBody, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] postForByte(String url, Map<String, String> headers, Map<String, String> uriVariables, Object body) {
        return restClient.post()
                .uri(url, uriVariables)
                .body(body)
                .retrieve()
                .body(byte[].class);
    }

}
