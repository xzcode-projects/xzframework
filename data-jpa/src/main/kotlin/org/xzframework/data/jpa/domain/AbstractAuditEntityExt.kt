package org.xzframework.data.jpa.domain

import java.io.Serializable


val <ID : Serializable, NAME : Serializable> AbstractAuditEntity<ID, NAME>.createdByOrNull: Auditor<ID?, NAME?>?
    get() = createdBy.orElse(null)
