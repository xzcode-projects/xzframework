package org.xzframewordk.sms;

import java.io.Serial;

public class SmsSendException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6100459986756746503L;
    private final SmsProperties properties;


    public SmsSendException(String message, SmsProperties properties, Throwable cause) {
        super(message, cause);
        this.properties = properties;
    }

    public SmsProperties getProperties() {
        return properties;
    }
}
