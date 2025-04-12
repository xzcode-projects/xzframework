package org.xzframework.data.querydsl

import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Path
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.PathBuilder
import com.querydsl.core.types.dsl.PathBuilderFactory
import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mapping.PropertyPath
import org.springframework.data.querydsl.QSort


fun <T, P> JPQLQuery<T>.applyPagination(pageable: Pageable, clazz: Class<P>): JPQLQuery<T> {
    if (pageable.isUnpaged) {
        return this
    }
    val pathBuilder = PathBuilderFactory().create(clazz)
    offset(pageable.offset)
    limit(pageable.pageSize.toLong())
    applySorting(this, pageable.sort, pathBuilder)
    return this
}

fun <T, P> JPQLQuery<T>.applySorting(sort: Sort, clazz: Class<P>): JPQLQuery<T> {
    val pathBuilder = PathBuilderFactory().create(clazz)
    applySorting(this, sort, pathBuilder)
    return this
}

private fun <T> applySorting(query: JPQLQuery<T>, sort: Sort, builder: PathBuilder<*>) {
    if (sort.isSorted) {
        if (sort is QSort) {
            addOrderByFrom(query, sort)
        }
        addOrderByFrom(query, sort, builder)
    }
}

private fun <T> addOrderByFrom(query: JPQLQuery<T>, sort: Sort, builder: PathBuilder<*>) {
    for (order in sort) {
        query.orderBy(toOrderSpecifier(order, builder))
    }
}

private fun <T> addOrderByFrom(query: JPQLQuery<T>, qSort: QSort): JPQLQuery<T> {
    val orderSpecifiers = qSort.orderSpecifiers
    return query.orderBy(*orderSpecifiers.toTypedArray())
}


private fun toOrderSpecifier(order: Sort.Order, builder: PathBuilder<*>): OrderSpecifier<*> {
    return OrderSpecifier(
        if (order.isAscending) Order.ASC else Order.DESC,
        buildOrderPropertyPathFrom(order, builder),
        toQueryDslNullHandling(order.nullHandling)
    )
}

private fun <T : Comparator<*>> buildOrderPropertyPathFrom(order: Sort.Order, builder: PathBuilder<*>): Expression<T> {
    var path: PropertyPath? = PropertyPath.from(order.property, builder.type!!)
    var sortPropertyExpression: Expression<*> = builder
    while (path != null) {
        sortPropertyExpression = if (!path.hasNext() && order.isIgnoreCase && String::class.java == path.type)
            Expressions.stringPath(sortPropertyExpression as Path<*>, path.segment).lower() //
        else
            Expressions.path(path.type, sortPropertyExpression as Path<*>, path.segment)
        path = path.next()
    }
    return sortPropertyExpression as Expression<T>
}

private fun toQueryDslNullHandling(nullHandling: Sort.NullHandling): OrderSpecifier.NullHandling {
    return when (nullHandling) {
        Sort.NullHandling.NULLS_FIRST -> OrderSpecifier.NullHandling.NullsFirst
        Sort.NullHandling.NULLS_LAST -> OrderSpecifier.NullHandling.NullsLast
        else -> OrderSpecifier.NullHandling.Default
    }
}
