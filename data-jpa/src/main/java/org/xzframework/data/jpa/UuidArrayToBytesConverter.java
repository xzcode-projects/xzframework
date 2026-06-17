package org.xzframework.data.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.nio.ByteBuffer;
import java.util.*;

@Converter
public class UuidArrayToBytesConverter implements AttributeConverter<Collection<UUID>, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(Collection<UUID> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return new byte[0];
        }
        // 每个UUID占用16字节
        ByteBuffer buffer = ByteBuffer.allocate(attribute.size() * 16);
        for (UUID uuid : attribute) {
            if (uuid != null) {
                buffer.putLong(uuid.getMostSignificantBits());
                buffer.putLong(uuid.getLeastSignificantBits());
            } else {
                // 对于null值，填充16个零字节
                buffer.put(new byte[16]);
            }
        }
        return buffer.array();
    }

    @Override
    public List<UUID> convertToEntityAttribute(byte[] dbData) {
        if (dbData == null || dbData.length == 0) {
            return Collections.emptyList();
        }
        // 每个UUID占用16字节
        int uuidCount = dbData.length / 16;
        List<UUID> result = new ArrayList<>();
        ByteBuffer buffer = ByteBuffer.wrap(dbData);
        for (int i = 0; i < uuidCount; i++) {
            long mostSignificantBits = buffer.getLong();
            long leastSignificantBits = buffer.getLong();
            // 检查是否为零字节（表示null值）
            if (mostSignificantBits == 0 && leastSignificantBits == 0) {
                result.add(null);
            } else {
                result.add(new UUID(mostSignificantBits, leastSignificantBits));
            }
        }
        return Collections.unmodifiableList(result);
    }

}
