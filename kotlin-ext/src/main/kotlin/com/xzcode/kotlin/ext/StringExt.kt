package com.xzcode.kotlin.ext

inline fun <T : CharSequence> T?.ifNotBlank(block: (T) -> Unit): T? {
    this?.takeIf { it.isNotBlank() }?.also {
        block(it)
    }
    return this
}

fun <T : CharSequence> T?.takeIfNotBlank(): T? {
    return this?.takeIf { it.isNotBlank() }
}
