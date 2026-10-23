package com.xzcode.kotlin.ext

import java.math.BigDecimal
import java.math.BigInteger

val HUNDRED = BigDecimal("100")
val THOUSAND = BigDecimal("1000")
val MILLION = BigDecimal("1000000")

fun Any?.toBigDecimal(): BigDecimal? {
    if (this == null) return null
    return when (this) {
        is BigDecimal -> this
        is Byte -> BigDecimal(toInt())
        is Short -> BigDecimal(toInt())
        is Int -> BigDecimal(this)
        is Long -> BigDecimal(this)
        is Float -> BigDecimal(toString())
        is Double -> BigDecimal(toString())
        is String -> BigDecimal(this)
        is BigInteger -> BigDecimal(this)
        else -> BigDecimal(toString())
    }
}

fun Any?.toBigDecimal(default: BigDecimal): BigDecimal {
    return this.toBigDecimal() ?: default
}


fun Any?.toLong(): Long? {
    if (this == null) return null
    return when (this) {
        is Long -> this
        is Number -> toLong()
        is String -> toLongOrNull()
        else -> toString().toLongOrNull()
    }
}

fun Any?.toLong(default: Long): Long {
    return toLong() ?: default
}

fun Any?.toInt(): Int? {
    if (this == null) return null
    return when (this) {
        is Int -> this
        is Number -> toInt()
        is String -> toIntOrNull()
        else -> toString().toIntOrNull()
    }
}

fun Any?.toInt(default: Int): Int {
    return toInt() ?: default
}
