package org.xzframework.data.id;

import java.util.UUID;

public class SimpleUuidGenerator implements UuidGenerator {

    @Override
    public UUID nextUuid() {
        return UUID.randomUUID();
    }

}
