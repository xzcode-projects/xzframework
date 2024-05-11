package org.xzframewordk.sms;

/**
 * 短信发送结果
 */
public record SmsResult(
        String msgId,
        String mobile,
        String signName,
        String templateCode,
        String params,
        String accessKeyId,
        String endPoint,
        String region
) {
}
