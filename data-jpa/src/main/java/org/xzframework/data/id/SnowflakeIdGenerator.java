package org.xzframework.data.id;

/**
 * 标准雪花算法实现
 * <p>
 * 雪花算法结构：
 * - 1位符号位：固定为0
 * - 41位时间戳：从2020-01-01 00:00:00开始的毫秒数
 * - 5位数据中心ID：最多支持32个数据中心
 * - 5位机器ID：每个数据中心最多支持32台机器
 * - 12位序列号：每毫秒最多生成4096个ID
 */
public class SnowflakeIdGenerator {

    // 开始时间戳 (2020-01-01 00:00:00)
    private static final long START_TIMESTAMP = 1577836800000L;

    // 数据中心ID位数
    private static final long DATACENTER_ID_BITS = 5L;
    // 机器ID位数
    private static final long MACHINE_ID_BITS = 5L;
    // 序列号位数
    private static final long SEQUENCE_BITS = 12L;

    // 数据中心ID最大值
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);
    // 机器ID最大值
    private static final long MAX_MACHINE_ID = ~(-1L << MACHINE_ID_BITS);
    // 序列号最大值
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    // 机器ID左移位数
    private static final long MACHINE_ID_SHIFT = SEQUENCE_BITS;
    // 数据中心ID左移位数
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + MACHINE_ID_BITS;
    // 时间戳左移位数
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + MACHINE_ID_BITS + DATACENTER_ID_BITS;

    // 数据中心ID
    private final long datacenterId;
    // 机器ID
    private final long machineId;
    // 序列号
    private long sequence = 0L;
    // 上次生成ID的时间戳
    private long lastTimestamp = -1L;

    /**
     * 构造函数
     *
     * @param datacenterId 数据中心ID，范围0-31
     * @param machineId 机器ID，范围0-31
     */
    public SnowflakeIdGenerator(long datacenterId, long machineId) {
        if (datacenterId < 0 || datacenterId > MAX_DATACENTER_ID) {
            throw new IllegalArgumentException("Datacenter ID must be between 0 and " + MAX_DATACENTER_ID);
        }
        if (machineId < 0 || machineId > MAX_MACHINE_ID) {
            throw new IllegalArgumentException("Machine ID must be between 0 and " + MAX_MACHINE_ID);
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 构造函数
     * <p>
     * 兼容旧版本，将机器ID拆分为数据中心ID和机器ID
     *
     * @param machineId 机器ID，范围0-1023
     */
    public SnowflakeIdGenerator(long machineId) {
        if (machineId < 0 || machineId > (MAX_DATACENTER_ID << MACHINE_ID_BITS) + MAX_MACHINE_ID) {
            throw new IllegalArgumentException("Machine ID must be between 0 and 1023");
        }
        this.datacenterId = machineId >> MACHINE_ID_BITS;
        this.machineId = machineId & MAX_MACHINE_ID;
    }

    /**
     * 生成ID
     *
     * @return 生成的ID
     */
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        // 处理时钟回拨
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
        }

        // 同一毫秒内，序列号自增
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 序列号溢出，等待下一毫秒
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 新的毫秒，序列号重置为0
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        // 组合ID：时间戳 + 数据中心ID + 机器ID + 序列号
        return ((timestamp - START_TIMESTAMP) << TIMESTAMP_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (machineId << MACHINE_ID_SHIFT)
                | sequence;
    }

    /**
     * 等待到下一毫秒
     *
     * @param lastTimestamp 上次生成ID的时间戳
     * @return 新的时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

}
