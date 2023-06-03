package com.xzframework.boot.autoconfigure.data;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver;
import org.springframework.data.web.ReactiveSortHandlerMethodArgumentResolver;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;
import org.xzframework.data.domain.RangePageable;
import org.xzframework.data.web.ReactiveRangePageableHandlerMethodArgumentResolver;


@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RangePageable.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class XzReactiveSpringDataWebConfiguration implements WebFluxConfigurer {
    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        configurer.addCustomResolver(
                new ReactiveSortHandlerMethodArgumentResolver(),
                new ReactivePageableHandlerMethodArgumentResolver(),
                new ReactiveRangePageableHandlerMethodArgumentResolver()
        );
    }
}

