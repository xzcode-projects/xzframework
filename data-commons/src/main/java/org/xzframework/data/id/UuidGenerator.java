package org.xzframework.data.id;

import java.util.UUID;

public interface UuidGenerator {

    /**
     * 生成下一个UUID
     *
     * @return 下一个UUID
     */
    UUID nextUuid();

}
