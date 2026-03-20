package org.xzframework.jackson;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

import java.time.ZonedDateTime;

public class ZonedDateTimeDeserializer extends StdDeserializer<ZonedDateTime> {

    public static final ZonedDateTimeDeserializer instance = new ZonedDateTimeDeserializer(ZonedDateTime.class);

    protected ZonedDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        String string = p.getString();
        if (string == null || string.isBlank()) {
            return null;
        } else {
            return ZonedDateTime.parse(string);
        }
    }

}
