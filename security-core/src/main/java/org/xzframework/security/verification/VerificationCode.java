package org.xzframework.security.verification;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

public record VerificationCode(
        String id,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String code,
        ZonedDateTime invalidateTime,
        String imgData
) implements Serializable {

    @Serial
    private static final long serialVersionUID = -6060058106901443139L;

    /**
     * 验证码默认的失效时常，单位是分钟
     */
    private static final int DEFAULT_INVALIDATE_TIME = 5;


    public VerificationCode(String id,
                            String code,
                            ZonedDateTime invalidateTime) {
        this(id, code, invalidateTime, null);
    }

    public VerificationCode(String id, String code) {
        this(id, code, null);
    }
 
    public VerificationCode withImageData(String imgData) {
        return new VerificationCode(
                this.id,
                this.code,
                this.invalidateTime,
                imgData
        );
    }

}
