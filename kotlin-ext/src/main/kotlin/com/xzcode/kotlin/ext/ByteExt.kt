package com.xzcode.kotlin.ext


fun List<ByteArray>.merge(): ByteArray {
    val totalSize = this.sumOf { it.size }
    val result = ByteArray(totalSize)
    var position = 0

    for (array in this) {
        array.copyInto(result, position)
        position += array.size
    }
    return result
}

fun ByteArray.split(chunkSize: Int): List<ByteArray> {
    require(chunkSize > 0) { "Chunk size must be positive" }
    require(this.size % chunkSize == 0) { "Byte array size must be multiple of chunk size" }

    return (0 until this.size / chunkSize).map { index ->
        val start = index * chunkSize
        val end = start + chunkSize
        this.copyOfRange(start, end)
    }
}
