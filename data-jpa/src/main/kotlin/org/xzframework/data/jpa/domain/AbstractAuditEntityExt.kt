package org.xzframework.data.jpa.domain

import com.querydsl.core.types.Predicate
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import java.io.Serializable


val <ID : Serializable, UID : Serializable?> AbstractAuditEntity<ID, UID>.createdByOrNull: Auditor<UID?>?
    get() = createdBy.orElse(null)

val <ID : Serializable, UID : Serializable?> AbstractAuditEntity<ID, UID>.lastModifiedByOrNull: Auditor<UID?>?
    get() = lastModifiedBy.orElse(null)

fun <T> QuerydslPredicateExecutor<T>.findOneOrNull(predicate: Predicate): T? = findOne(predicate).orElse(null)
