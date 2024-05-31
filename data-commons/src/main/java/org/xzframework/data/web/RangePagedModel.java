package org.xzframework.data.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.lang.Nullable;
import org.xzframework.data.domain.RangePage;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 基于Spring Boot 3.3的PagedModel,构建此 model
 *
 * @param <T>
 * @param <M>
 */
public class RangePagedModel<T, M extends Comparable<?> & Serializable> {

    private final M max;

    private final Page<T> page;

    public RangePagedModel(RangePage<T, M> rangePage) {
        page = rangePage;
        max = rangePage.getMax();
    }

    public RangePagedModel(M max, Page<T> page) {
        this.max = max;
        this.page = page;
    }

    @JsonProperty
    public List<T> getContent() {
        return page.getContent();
    }

    @JsonProperty
    public M getMax() {
        return max;
    }

    @Nullable
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
}