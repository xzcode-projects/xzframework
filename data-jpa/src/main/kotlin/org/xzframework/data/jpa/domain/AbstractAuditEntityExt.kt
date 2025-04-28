package org.xzframework.data.jpa.domain

import com.querydsl.core.types.Predicate
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import java.io.Serializable


val <ID : Serializable> AbstractAuditEntity<ID>.createdByOrNull: Auditor<ID?>?
    get() = createdBy.orElse(null)

val <ID : Serializable> AbstractAuditEntity<ID>.lastModifiedByOrNull: Auditor<ID?>?
    get() = lastModifiedBy.orElse(null)

fun <T> QuerydslPredicateExecutor<T>.findOneOrNull(predicate: Predicate): T? = findOne(predicate).orElse(null)
