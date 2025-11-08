package org.xzframework.data.jpa.domain

import java.io.Serializable

// 修改类型参数约束，使用Kotlin允许的语法格式
val <ID, UID> AbstractAuditEntity<ID, UID>.createdByOrNull: Auditor<UID>? where ID : Serializable, ID : Comparable<ID>, UID : Serializable, UID : Comparable<UID>
    get() {
        return createdBy.orElse(null)
    }

// 同样修改类型参数约束，并确保返回类型一致为可空
val <ID, UID> AbstractAuditEntity<ID, UID>.lastModifiedByOrNull: Auditor<UID>? where ID : Serializable, ID : Comparable<ID>, UID : Serializable, UID : Comparable<UID>
    get() {
        return lastModifiedBy.orElse(null)
    }
