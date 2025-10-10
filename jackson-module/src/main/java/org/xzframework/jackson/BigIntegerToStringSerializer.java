package org.xzframework.jackson;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

import java.math.BigInteger;

public class BigIntegerToStringSerializer extends StdSerializer<BigInteger> {

    public static final BigIntegerToStringSerializer instance = new BigIntegerToStringSerializer(BigInteger.class);

    //    private static final Long MAX_SAFE_INTEGER = 9007199254740991L;
    private static final BigInteger MAX_SAFE_INTEGER = new BigInteger("9000000000000000");
    //    private static final Long MIN_SAFE_INTEGER = -9007199254740991L;
    private static final BigInteger MIN_SAFE_INTEGER = new BigInteger("-9000000000000000");

    protected BigIntegerToStringSerializer(Class<?> t) {
        super(t);
    }

    @Override
    public void serialize(BigInteger value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        if (value == null) {
            gen.writeNull();
        } else if (value.compareTo(MIN_SAFE_INTEGER) > 0 && value.compareTo(MAX_SAFE_INTEGER) < 0) {
            gen.writeNumber(value);
        } else {
            gen.writeString(value.toString());
        }
    }

}
