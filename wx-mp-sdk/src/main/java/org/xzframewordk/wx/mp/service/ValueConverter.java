package org.xzframewordk.wx.mp.service;

public interface ValueConverter {
    <R> R apply(String body, Class<R> valueType);
}
