package com.xzframework.boot.autoconfigure.data;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.xzframework.data.domain.RangePageable;
import org.xzframework.data.web.RangePageableArgumentResolver;
import org.xzframework.data.web.RangePageableHandlerMethodArgumentResolver;

import java.util.List;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RangePageable.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class XzSpringDataWebConfiguration implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(rangePageableArgumentResolver());
    }

    private RangePageableArgumentResolver rangePageableArgumentResolver() {
        return new RangePageableHandlerMethodArgumentResolver();
    }
}
