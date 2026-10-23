package com.xzcode.kotlin.ext

import java.nio.ByteBuffer
import java.util.*

fun UUID.toByteArray(): ByteArray {
    return ByteBuffer.allocate(16)
        .putLong(this.mostSignificantBits)
        .putLong(this.leastSignificantBits)
        .array()
}

fun ByteArray.toUUID(): UUID {
    require(this.size == 16) { "Byte array must be exactly 16 bytes long to convert to UUID" }
    val buffer = ByteBuffer.wrap(this)
    val mostSignificantBits = buffer.long
    val leastSignificantBits = buffer.long
    return UUID(mostSignificantBits, leastSignificantBits)
}

fun ByteArray?.toUUIDList(): List<UUID> {
    return this?.split(16)?.map { it.toUUID() } ?: emptyList()
}

fun List<UUID>.toByteArray(): ByteArray {
    return this.map {
        it.toByteArray()
    }.merge()
}
