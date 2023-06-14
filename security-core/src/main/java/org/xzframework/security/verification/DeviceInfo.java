package org.xzframework.security.verification;

import java.io.Serializable;

public interface DeviceInfo extends Serializable {
    /**
     * 设备识别码
     */
    String getDeviceKey();
}
