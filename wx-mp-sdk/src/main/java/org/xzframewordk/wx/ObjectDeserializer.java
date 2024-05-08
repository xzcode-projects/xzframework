package org.xzframewordk.wx;

public interface ObjectDeserializer {
   <R> R deserialize(String body, Class<R> valueType);
}
