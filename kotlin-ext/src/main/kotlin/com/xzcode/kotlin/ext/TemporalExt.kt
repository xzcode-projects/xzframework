package com.xzcode.kotlin.ext

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun CharSequence?.toLocalDate(formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME): LocalDate? {
    return this?.let { LocalDate.parse(it, formatter) }
}

fun Any?.toLocalDate(): LocalDate? {
    return this?.toString()?.let {
        try {
            LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE)
        } catch (t: Throwable) {
            try {
                LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            } catch (t2: Throwable) {
                null
            }
        }
    }
}

fun Any?.toLocalDate(formatter: DateTimeFormatter): LocalDate? {
    return try {
        this?.toString()?.let {
            LocalDate.parse(it, formatter)
        }
    } catch (t: Throwable) {
        null
    }
}

fun CharSequence?.toZonedDateTime(
    zoneId: ZoneId = ZoneId.of("Asia/Shanghai")
): ZonedDateTime? {
    return this?.let {
        try {
            ZonedDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME).withZoneSameInstant(zoneId)
        } catch (e: Throwable) {
            try {
                ZonedDateTime.of(LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME), zoneId)
            } catch (e2: Throwable) {
                ZonedDateTime.of(LocalDate.parse(it).atStartOfDay(), zoneId)
            }
        }
    }
}

fun Any?.toZonedDateTime(
    zoneId: ZoneId = ZoneId.of("Asia/Shanghai")
): ZonedDateTime? {
    return this?.let {
        when (it) {
            is ZonedDateTime -> it.withZoneSameInstant(zoneId)
            is LocalDateTime -> ZonedDateTime.of(it, zoneId)
            is LocalDate -> ZonedDateTime.of(it.atStartOfDay(), zoneId)
            is CharSequence -> it.toZonedDateTime(zoneId)
            else -> throw RuntimeException("not supported source type")
        }
    }
}

