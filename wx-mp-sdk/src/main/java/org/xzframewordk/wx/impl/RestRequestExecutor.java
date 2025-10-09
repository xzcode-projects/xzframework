package org.xzframewordk.wx.impl;

import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.xzframewordk.wx.ObjectDeserializer;
import org.xzframewordk.wx.WxRequestExecutor;

import java.util.Map;

public class RestRequestExecutor implements WxRequestExecutor {

    private final RestOperations restOperations;

    private final ObjectDeserializer converter;

    public RestRequestExecutor(
            RestOperations restOperations,
            ObjectDeserializer valueConverter
    ) {
        this.restOperations = restOperations;
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

            HttpHeaders header = new HttpHeaders();
            headers.forEach(header::add);
            HttpEntity<@NonNull B> entity = new HttpEntity<>(body, header);
            ResponseEntity<@NonNull String> responseEntity = restOperations.exchange(
                    url,
                    HttpMethod.valueOf(method),
                    entity,
                    String.class,
                    uriVariables
            );
            String responseBody = responseEntity.getBody();
            return converter.deserialize(responseBody, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] postForByte(String url, Map<String, String> headers, Map<String, String> uriVariables, Object body) {
        return restOperations.postForObject(
                url,
                body,
                byte[].class,
                uriVariables
        );
    }

}
