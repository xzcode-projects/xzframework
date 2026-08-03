package org.xzframework.jackson;

import tools.jackson.core.json.PackageVersion;
import tools.jackson.databind.module.SimpleModule;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.BigInteger;

public class XzSafeNumberModule extends SimpleModule {

    @Serial
    private static final long serialVersionUID = -3318884625899616547L;

    public XzSafeNumberModule() {
        super(XzSafeNumberModule.class.getName(), PackageVersion.VERSION);
        // 不能在复写的setupModule调用super.setupModule(context)之后添加序列化器
        // 否则不生效
        addSerializer(BigDecimal.class, BigDecimalToStripTrailingZeroPlanStringSerializer.INSTANCE);
        addSerializer(BigInteger.class, BigIntegerToStringSerializer.INSTANCE);
        addSerializer(Long.class, LongToStringSerializer.INSTANCE);
        addSerializer(Long.TYPE, LongToStringSerializer.INSTANCE);
        // addDeserializer(ZonedDateTime.class, ZonedDateTimeDeserializer.instance);
    }

}
