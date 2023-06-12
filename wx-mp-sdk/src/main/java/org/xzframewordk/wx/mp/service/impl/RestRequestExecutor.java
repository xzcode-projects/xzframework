package org.xzframewordk.wx.mp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.xzframewordk.wx.mp.service.ValueConverter;
import org.xzframewordk.wx.mp.service.WxRequestExecutor;

import java.util.Map;


public class RestRequestExecutor implements WxRequestExecutor {

    private final RestOperations restOperations;

    private final ValueConverter converter;

    public RestRequestExecutor() {
        this(new RestTemplate(), new ObjectMapper());
    }

    public RestRequestExecutor(RestOperations restOperations, ObjectMapper objectMapper) {
        this(restOperations, new ValueConverter() {
            @Override
            public <R> R apply(String body, Class<R> valueType) {
                try {
                    return objectMapper.readValue(body, valueType);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public RestRequestExecutor(
            RestOperations restOperations,
            ValueConverter valueConverter
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
            LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
            headers.forEach(header::add);
            HttpEntity<B> entity = new HttpEntity<>(body, header);
            ResponseEntity<String> responseEntity = restOperations.exchange(
                    url,
                    HttpMethod.valueOf(method),
                    entity,
                    String.class,
                    uriVariables
            );
            String responseBody = responseEntity.getBody();
            return converter.apply(responseBody, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
