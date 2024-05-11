package org.xzframewordk.sms.support;


import org.xzframewordk.sms.Sms;
import org.xzframewordk.sms.SmsConfigRepository;
import org.xzframewordk.sms.SmsProperties;

public class FixedSmsConfigRepository implements SmsConfigRepository {
    private final SmsProperties properties;

    public FixedSmsConfigRepository(SmsProperties properties) {
        this.properties = properties;
    }

    @Override
    public SmsProperties getProperties(Sms sms) {
        return properties;
    }
}
