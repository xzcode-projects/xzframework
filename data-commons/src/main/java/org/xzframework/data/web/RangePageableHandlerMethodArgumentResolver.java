package org.xzframework.data.web;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolverSupport;
import org.springframework.data.web.SortArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.xzframework.data.domain.RangePageRequest;
import org.xzframework.data.domain.RangePageable;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

public class RangePageableHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolverSupport
        implements RangePageableArgumentResolver {

    private static final SortHandlerMethodArgumentResolver DEFAULT_SORT_RESOLVER = new SortHandlerMethodArgumentResolver();

    private final SortArgumentResolver sortResolver;

    private final Function<String, ? extends Serializable> maxArgumentResolver = input -> Objects.isNull(input) ? null : Long.decode(input);

    /**
     * Constructs an instance of this resolved with a default {@link SortHandlerMethodArgumentResolver}.
     */
    public RangePageableHandlerMethodArgumentResolver() {
        this((SortArgumentResolver) null);
    }

    public RangePageableHandlerMethodArgumentResolver(SortHandlerMethodArgumentResolver sortResolver) {
        this((SortArgumentResolver) sortResolver);
    }

    public RangePageableHandlerMethodArgumentResolver(@Nullable SortArgumentResolver sortResolver) {
        this.sortResolver = sortResolver == null ? DEFAULT_SORT_RESOLVER : sortResolver;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return RangePageable.class.equals(parameter.getParameterType());
    }

    @Override
    public RangePageable<?> resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String page = webRequest.getParameter(getParameterNameToUse(getPageParameterName(), methodParameter));
        String pageSize = webRequest.getParameter(getParameterNameToUse(getSizeParameterName(), methodParameter));
        String max = webRequest.getParameter(getParameterNameToUse(getMaxParameterName(), methodParameter));
        Sort sort = sortResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
        Pageable pageable = getPageable(methodParameter, page, pageSize);
        if (sort.isSorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
        return RangePageRequest.of(pageable, maxArgumentResolver.apply(max));
    }

    private String getMaxParameterName() {
        return "max";
    }
}
