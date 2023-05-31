package org.xzframework.data.domain;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 有限范围内的分页
 *
 * @param <T> 数据类型
 * @param <M> 最大值的类型
 * @author ldwqh0@outlook.com
 */
public interface RangePage<T, M extends Comparable<?> & Serializable> extends Page<T> {
    static <T, M extends Comparable<?> & Serializable> RangePage<T, M> of(M max, Page<T> page) {
        return new RangePageImpl<>(max, page.getContent(), page.getPageable(), page.getTotalElements());
    }

    /**
     * 范围的上限
     *
     * @return 返回上限信息
     */
    M getMax();

    @Override
    <U> RangePage<U, M> map(Function<? super T, ? extends U> converter);
}
