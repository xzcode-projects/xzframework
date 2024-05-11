package org.xzframewordk.sms.support;


import org.xzframewordk.sms.Sms;
import org.xzframewordk.sms.SmsManager;
import org.xzframewordk.sms.SmsResult;

import java.util.UUID;

/**
 * 使用控制台发送短信。仅仅用于测试
 */
public class ConsoleSmsManager implements SmsManager {

    @Override
    public SmsResult sendSms(Sms sms) {
        String body = sms.getParams();
        return new SmsResult(
                UUID.randomUUID().toString(),
                sms.getMobile(),
                sms.getSignName(),
                sms.getTemplate(),
                body,
                "",
                "",
                ""
        );
    }
}
