package org.xzframework.data.domain

import java.io.Serializable

val <T : Serializable> RangePageable<T>.maxOrNull: T?
    get() = max.orElse(null)
