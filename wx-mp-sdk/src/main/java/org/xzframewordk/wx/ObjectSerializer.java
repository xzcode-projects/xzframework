package org.xzframewordk.wx;

public interface ObjectSerializer {
    <T> String serialize(T value);
}
