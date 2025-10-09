package org.xzframework.jackson;

import tools.jackson.core.json.PackageVersion;
import tools.jackson.databind.module.SimpleModule;

import java.io.Serial;

public class XzSafeNumberModule extends SimpleModule {

    @Serial
    private static final long serialVersionUID = -3318884625899616547L;

    public XzSafeNumberModule() {
        super(XzSafeNumberModule.class.getName(), PackageVersion.VERSION);
    }

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);
        addSerializer(BigDecimalToStripTrailingZeroPlanStringSerializer.instance);
        addSerializer(BigIntegerToStringSerializer.instance);
        addSerializer(LongToStringSerializer.instance);
    }

}
