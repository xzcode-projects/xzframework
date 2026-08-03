package com.xzframework.boot.aot;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

public class FrameworkRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.reflection()
                .registerType(TypeReference.of("kotlin.collections.EmptyList"))
                .registerType(TypeReference.of("kotlin.collections.EmptyMap"))
                .registerType(TypeReference.of("kotlin.collections.EmptySet"))
                .registerType(TypeReference.of("java.util.Collections$SingletonList"))
                .registerType(TypeReference.of("java.util.Collections$SingletonMap"))
                .registerType(TypeReference.of("java.util.Collections$SingletonSet"))
                .registerType(TypeReference.of("java.util.Collections$UnmodifiableList"))
                .registerType(TypeReference.of("java.util.Collections$UnmodifiableMap"))
                .registerType(TypeReference.of("java.util.Collections$UnmodifiableSet"))
                .registerType(TypeReference.of("java.util.Collections$UnmodifiableSortedMap"))
                .registerType(TypeReference.of("java.util.Collections$UnmodifiableSortedSet"))
                .registerType(TypeReference.of("java.util.Collections$EmptyList"))
                .registerType(TypeReference.of("java.util.Collections$EmptyMap"))
                .registerType(TypeReference.of("java.util.Collections$EmptySet"))
                .registerType(TypeReference.of("java.util.Collections$SynchronizedList"))
                .registerType(TypeReference.of("java.util.Collections$SynchronizedMap"))
                .registerType(TypeReference.of("java.util.Collections$SynchronizedSet"))
                .registerType(TypeReference.of("java.util.Collections$CheckedList"))
                .registerType(TypeReference.of("java.util.Collections$CheckedMap"))
                .registerType(TypeReference.of("java.util.Collections$CheckedSet"))
                .registerType(TypeReference.of("java.util.Collections$CopiesList"))
        ;
    }

}
