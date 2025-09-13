package com.xzframework.boot.autoconfigure.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;


public class LongToStringSerializer extends JsonSerializer<Long> {
    public static final LongToStringSerializer instance = new LongToStringSerializer();

    //    private static final Long MAX_SAFE_INTEGER = 9007199254740991L;
    public static final long MAX_SAFE_INTEGER = 9000000000000000L;
    //    private static final Long MIN_SAFE_INTEGER = -9007199254740991L;
    public static final long MIN_SAFE_INTEGER = -9000000000000000L;

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else if (value >= MIN_SAFE_INTEGER && value <= MAX_SAFE_INTEGER) {
            gen.writeString(value.toString());
        } else {
            gen.writeNumber(value);
        }
    }
}
