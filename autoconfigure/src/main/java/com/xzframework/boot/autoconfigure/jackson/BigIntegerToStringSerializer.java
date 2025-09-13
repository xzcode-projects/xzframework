package com.xzframework.boot.autoconfigure.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigInteger;


public class BigIntegerToStringSerializer extends JsonSerializer<BigInteger> {
    public static final BigIntegerToStringSerializer instance = new BigIntegerToStringSerializer();

    //    private static final Long MAX_SAFE_INTEGER = 9007199254740991L;
    private static final BigInteger MAX_SAFE_INTEGER = new BigInteger("9000000000000000");
    //    private static final Long MIN_SAFE_INTEGER = -9007199254740991L;
    private static final BigInteger MIN_SAFE_INTEGER = new BigInteger("-9000000000000000");

    @Override
    public void serialize(BigInteger value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        if (value == null) {
            gen.writeNull();
        } else if (value.compareTo(MIN_SAFE_INTEGER) > 0 && value.compareTo(MAX_SAFE_INTEGER) < 0) {
            gen.writeNumber(value);
        } else {
            gen.writeString(value.toString());
        }
    }
}
