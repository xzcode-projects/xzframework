package com.xzframework.boot.autoconfigure.jackson;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.math.BigInteger;

@Configuration
@ConditionalOnClass(ToStringSerializer.class)
public class NumberToStringJacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer numberToStringJacksonCustomizer() {
        return it -> {
            it.serializerByType(Long.class, ToStringSerializer.instance);
            it.serializerByType(Long.TYPE, ToStringSerializer.instance);
            it.serializerByType(BigDecimal.class, ToStringSerializer.instance);
            it.serializerByType(BigInteger.class, ToStringSerializer.instance);
        };
    }
}
