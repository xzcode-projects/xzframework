package org.xzframework.data.jpa.domain

import com.querydsl.core.types.Predicate
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import java.io.Serializable


val <ID : Serializable, NAME : Serializable> AbstractAuditEntity<ID, NAME>.createdByOrNull: Auditor<ID?, NAME?>?
    get() = createdBy.orElse(null)

val <ID : Serializable, NAME : Serializable> AbstractAuditEntity<ID, NAME>.lastModifiedByOrNull: Auditor<ID?, NAME?>?
    get() = lastModifiedBy.orElse(null)

fun <T> QuerydslPredicateExecutor<T>.findOneOrNull(predicate: Predicate): T? = findOne(predicate).orElse(null)
