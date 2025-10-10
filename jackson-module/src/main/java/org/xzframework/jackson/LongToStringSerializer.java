package org.xzframework.jackson;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

public class LongToStringSerializer extends StdSerializer<Long> {

    public static final LongToStringSerializer instance = new LongToStringSerializer(Long.class);

    //    private static final Long MAX_SAFE_INTEGER = 9007199254740991L;
    private static final long MAX_SAFE_LONG = 9000000000000000L;
    //    private static final Long MIN_SAFE_INTEGER = -9007199254740991L;
    private static final long MIN_SAFE_LONG = -9000000000000000L;

    protected LongToStringSerializer(Class<?> t) {
        super(t);
    }

    @Override
    public void serialize(Long value, tools.jackson.core.JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        if (value == null) {
            gen.writeNull();
        } else if (value >= MIN_SAFE_LONG && value <= MAX_SAFE_LONG) {
            gen.writeNumber(value);
        } else {
            gen.writeString(value.toString());
        }
    }

}
