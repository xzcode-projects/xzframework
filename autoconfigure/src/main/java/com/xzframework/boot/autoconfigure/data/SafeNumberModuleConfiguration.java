package com.xzframework.boot.autoconfigure.data;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xzframework.jackson.XzSafeNumberModule;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(XzSafeNumberModule.class)
public class SafeNumberModuleConfiguration {

    @Bean
    public XzSafeNumberModule safeNumberModule() {
        return new XzSafeNumberModule();
    }

}
