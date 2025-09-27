package org.xzframework.data.jpa.domain

import java.io.Serializable


val <ID : Serializable, UID : Serializable?> AbstractAuditEntity<ID, UID>.createdByOrNull: Auditor<UID?>?
    get() = createdBy.orElse(null)

val <ID : Serializable, UID : Serializable?> AbstractAuditEntity<ID, UID>.lastModifiedByOrNull: Auditor<UID?>?
    get() = lastModifiedBy.orElse(null)
