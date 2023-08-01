package org.xzframework.security.verification;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

public class DeviceVerificationCode implements Serializable {

    @Serial
    private static final long serialVersionUID = -6060058106901443139L;

    /**
     * 验证码ID
     */
    private final String id;

    /**
     * 验证码设备键
     */
    private final String key;

    private final String code;

    private final ZonedDateTime createdTime = ZonedDateTime.now();

    private final ZonedDateTime expireAt;

    public DeviceVerificationCode(String key, String code) {
        this(UUID.randomUUID().toString(), key, code);
    }

    public DeviceVerificationCode(String id, String key, String code) {
        this(id, key, code, ZonedDateTime.now().plusMinutes(5));
    }

    public DeviceVerificationCode(String id, String key, String code, ZonedDateTime expireAt) {
        this.id = id;
        this.key = key;
        this.code = code;
        this.expireAt = expireAt;
    }

    /**
     * 验证码ID
     *
     * @return 验证码ID字符串
     */
    public String getId() {
        return this.id;
    }

    /**
     * 要验证的第三方工具，通常是手机号，邮箱地址等
     */
    @JsonIgnore
    public String getKey() {
        return this.key;
    }

    /**
     * 生成的验证码
     */
    @JsonIgnore
    public String getCode() {
        return this.code;
    }

    /**
     * 验证码生成时间
     */
    public ZonedDateTime getCreatedTime() {
        return this.createdTime;
    }

    /**
     * 失效时间
     */
    public ZonedDateTime getExpireAt() {
        return this.expireAt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + createdTime.hashCode();
        result = prime * result + ((expireAt == null) ? 0 : expireAt.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DeviceVerificationCode other = (DeviceVerificationCode) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (!createdTime.equals(other.createdTime))
            return false;
        if (expireAt == null) {
            if (other.expireAt != null)
                return false;
        } else if (!expireAt.equals(other.expireAt))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (key == null) {
            return other.key == null;
        } else return key.equals(other.key);
    }

    @Override
    public String toString() {
        return "DeviceVerificationCode{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", code='" + code + '\'' +
                ", createdTime=" + createdTime +
                ", expireAt=" + expireAt +
                '}';
    }
}
