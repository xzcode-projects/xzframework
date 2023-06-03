package org.xzframework.data.web;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolverSupport;
import org.springframework.data.web.ReactiveSortHandlerMethodArgumentResolver;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.SyncHandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import org.xzframework.data.domain.RangePageRequest;
import org.xzframework.data.domain.RangePageable;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

public class ReactiveRangePageableHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolverSupport implements SyncHandlerMethodArgumentResolver {

    private static final ReactiveSortHandlerMethodArgumentResolver DEFAULT_SORT_RESOLVER = new ReactiveSortHandlerMethodArgumentResolver();
    private final ReactiveSortHandlerMethodArgumentResolver sortResolver;

    private final Function<String, ? extends Serializable> maxArgumentResolver = input -> Objects.isNull(input) ? null : Long.decode(input);


    public ReactiveRangePageableHandlerMethodArgumentResolver() {
        this(DEFAULT_SORT_RESOLVER);
    }

    public ReactiveRangePageableHandlerMethodArgumentResolver(ReactiveSortHandlerMethodArgumentResolver sortResolver) {
        Assert.notNull(sortResolver, "ReactiveSortHandlerMethodArgumentResolver must not be null");
        this.sortResolver = sortResolver;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return RangePageable.class.equals(parameter.getParameterType());
    }

    @Override
    public RangePageable<?> resolveArgumentValue(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        String page = queryParams.getFirst(getParameterNameToUse(getPageParameterName(), parameter));
        String pageSize = queryParams.getFirst(getParameterNameToUse(getSizeParameterName(), parameter));
        String max = queryParams.getFirst(getParameterNameToUse(getMaxParameterName(), parameter));
        Sort sort = sortResolver.resolveArgumentValue(parameter, bindingContext, exchange);
        Pageable pageable = getPageable(parameter, page, pageSize);
        if (sort.isSorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
        return RangePageRequest.of(pageable, maxArgumentResolver.apply(max));
    }

    private String getMaxParameterName() {
        return "max";
    }
}
