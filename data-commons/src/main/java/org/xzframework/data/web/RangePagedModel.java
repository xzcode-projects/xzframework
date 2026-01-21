package org.xzframework.data.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * 基于Spring Boot 3.3的PagedModel,构建此 model
 *
 * @param <T>
 * @param <M>
 */
public class RangePagedModel<T, M extends Comparable<?> & Serializable> implements Iterable<T> {

    private final @Nullable M max;

    private final @NonNull Page<T> page;

    public RangePagedModel() {
        this(null, Page.empty());
    }

    public RangePagedModel(@Nullable M max, @NonNull Page<T> page) {
        this.max = max;
        this.page = page;
    }

    public static <T, M extends Comparable<?> & Serializable> RangePagedModel<T, M> empty() {
        return new RangePagedModel<>(null, Page.empty());
    }

    @JsonProperty
    @NonNull
    public List<T> getContent() {
        return page.getContent();
    }

    @Nullable
    @JsonProperty
    public M getMax() {
        return max;
    }

    @JsonProperty("last")
    public boolean isLast() {
        return page.isLast();
    }

    @JsonProperty("page")
    public PagedModel.PageMetadata getMetadata() {
        return new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RangePagedModel<?, ?> that)) return false;
        return Objects.equals(max, that.max) && Objects.equals(page, that.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(max, page);
    }

    @NonNull
    public <R> RangePagedModel<R, M> map(@NonNull Function<T, R> convert) {
        return new RangePagedModel<>(max, page.map(convert));
    }

    @NonNull
    @Override
    @JsonIgnore
    public Iterator<T> iterator() {
        return page.iterator();
    }

}
