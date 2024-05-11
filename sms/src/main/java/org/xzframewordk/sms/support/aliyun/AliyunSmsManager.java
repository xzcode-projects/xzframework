package org.xzframewordk.sms.support.aliyun;


import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import darabonba.core.client.ClientOverrideConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xzframewordk.sms.*;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

public class AliyunSmsManager implements SmsManager {
    private static final Logger log = LoggerFactory.getLogger(AliyunSmsManager.class);
    private final SmsConfigRepository smsConfigRepository;

    public AliyunSmsManager(SmsConfigRepository smsConfigRepository) {
        this.smsConfigRepository = smsConfigRepository;
    }

    private AsyncClient buildClient(SmsProperties config) {
        return AsyncClient.builder()
                .region(config.getRegion())
                .credentialsProvider(
                        StaticCredentialProvider.create(
                                Credential
                                        .builder()
                                        .accessKeyId(config.getAccessKeyId())
                                        .accessKeySecret(config.getAccessKeySecret())
                                        .build()
                        )
                )
                .overrideConfiguration(
                        ClientOverrideConfiguration
                                .create()
                                .setEndpointOverride(config.getEndPoint())
                                .setConnectTimeout(Duration.ofSeconds(30))
                ).build();
    }


    public SmsResult sendSms(Sms sms) {
        SmsProperties properties = smsConfigRepository.getProperties(sms);
        SendSmsRequest request = SendSmsRequest.builder()
                .phoneNumbers(sms.getMobile())
                .signName(sms.getSignName())
                .templateCode(sms.getTemplate())
                .templateParam(sms.getParams())
                .build();
        try (AsyncClient client = buildClient(properties)) {
            return client.sendSms(request)
                    .thenApply(it -> {
                        if (Objects.equals(it.getBody().getCode(), "OK")) {
                            log.error("发送短信时发送错误。响应体为[{}]", it.getBody());
                            if (it.getBody().getMessage().contains("流控")) {
                                throw new RuntimeException("触发流量并发控制");
                            } else {
                                throw new RuntimeException(it.getBody().getMessage());
                            }
                        } else {
                            log.debug("信息发送完成");
                            return new SmsResult(
                                    it.getBody().getBizId(),
                                    sms.getMobile(),
                                    sms.getSignName(),
                                    sms.getTemplate(),
                                    sms.getParams(),
                                    properties.getAccessKeyId(),
                                    properties.getEndPoint(),
                                    properties.getRegion()
                            );
                        }
                    }).exceptionallyAsync((it) -> {
                        log.error("发送短信时发生错误", it);
                        throw new SmsSendException(it.getMessage(), properties, it);
                    }).get();
        } catch (Throwable throwable) {
            log.error("发送短信时发生错误", throwable);
            throw new SmsSendException(Optional.ofNullable(throwable.getMessage()).orElse("未知错误"), properties, throwable);
        }
    }
}
