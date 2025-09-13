package com.xzframework.boot.autoconfigure.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalToStripTrailingZeroPlanStringSerializer extends JsonSerializer<BigDecimal> {

    public final static BigDecimalToStripTrailingZeroPlanStringSerializer instance = new BigDecimalToStripTrailingZeroPlanStringSerializer();

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            String str = value.stripTrailingZeros().toPlainString();
            if (str.length() > 15) {
                gen.writeString(str);
            } else {
                gen.writeNumber(str);
            }
        }
    }
}
