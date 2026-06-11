package org.xzframework.data.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SoftDeleteConverter implements AttributeConverter<Boolean, Long> {

    @Override
    public Long convertToDatabaseColumn(Boolean attribute) {
        if (attribute != null && attribute) {
            return System.currentTimeMillis();
        } else {
            return 0L;
        }
    }

    @Override
    public Boolean convertToEntityAttribute(Long dbData) {
        return dbData != null && dbData != 0;
    }

}
