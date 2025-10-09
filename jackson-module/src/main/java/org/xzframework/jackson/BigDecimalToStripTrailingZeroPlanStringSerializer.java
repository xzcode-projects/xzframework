package org.xzframework.jackson;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.math.BigDecimal;

public class BigDecimalToStripTrailingZeroPlanStringSerializer extends ValueSerializer<BigDecimal> {

    public final static BigDecimalToStripTrailingZeroPlanStringSerializer instance = new BigDecimalToStripTrailingZeroPlanStringSerializer();

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
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
