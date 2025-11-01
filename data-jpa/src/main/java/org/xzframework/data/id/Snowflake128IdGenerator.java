package org.xzframework.data.id;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class Snowflake128IdGenerator implements UuidGenerator {

    // ============================== 常量 ==============================
    /**
     * 起始时间戳（2024-01-01 00:00:00，可自定义）
     */
    private final long START_TIMESTAMP = 1704067200000L;

    // 位分配方案（总计128位）
    // 高64位：0(符号位) + 41位时间戳 + 22位预留
    // 低64位：10位数据中心ID + 10位机器ID + 44位序列号

    /**
     * 时间戳所占位数
     */
    private final long TIMESTAMP_BITS = 41L;
    /**
     * 数据中心ID所占位数
     */
    private final long DATA_CENTER_ID_BITS = 10L;
    /**
     * 机器ID所占位数
     */
    private final long WORKER_ID_BITS = 10L;
    /**
     * 序列号所占位数
     */
    private final long SEQUENCE_BITS = 44L;
    /**
     * 高64位中的预留位所占位数
     */
    private final long HIGH_RESERVED_BITS = 22L;

    /**
     * 数据中心ID最大值（2^10 - 1）
     */
    private final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);
    /**
     * 机器ID最大值（2^10 - 1）
     */
    private final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    /**
     * 序列号最大值（2^44 - 1）
     */
    private final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    /**
     * 机器ID在低64位中的左移位数
     */
    private final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    /**
     * 数据中心ID在低64位中的左移位数
     */
    private final long DATA_CENTER_ID_SHIFT = WORKER_ID_BITS + WORKER_ID_SHIFT;

    // ============================== 变量 ==============================
    /**
     * 数据中心ID（0 ~ MAX_DATA_CENTER_ID）
     */
    private final long dataCenterId;
    /**
     * 机器ID（0 ~ MAX_WORKER_ID）
     */
    private final long workerId;

    /**
     * 序列号（0 ~ MAX_SEQUENCE）
     */
    private long sequence = 0L;
    /**
     * 上次生成ID的时间戳
     */
    private long lastTimestamp = -1L;

    // ============================== 构造函数 ==============================

    /**
     * 初始化生成器
     *
     * @param dataCenterId 数据中心ID（0 ~ 1023）
     * @param workerId     机器ID（0 ~ 1023）
     */
    public Snowflake128IdGenerator(long dataCenterId, long workerId) {
        if (dataCenterId < 0 || dataCenterId > MAX_DATA_CENTER_ID) {
            throw new IllegalArgumentException("dataCenterId 超出范围（0 ~ " + MAX_DATA_CENTER_ID + "）");
        }
        if (workerId < 0 || workerId > MAX_WORKER_ID) {
            throw new IllegalArgumentException("workerId 超出范围（0 ~ " + MAX_WORKER_ID + "）");
        }
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
    }

    // 简化构造函数，使用默认参数
    public Snowflake128IdGenerator() {
        this(0, 0);
    }

    // ============================== 核心方法 ==============================

    /**
     * 将128位字节数组ID转换为UUID
     *
     * @param idBytes 128位ID的字节数组形式
     * @return UUID对象
     * @throws IllegalArgumentException 如果输入字节数组长度不是16
     */
    public static UUID toUUID(byte[] idBytes) {
        if (idBytes == null || idBytes.length != 16) {
            throw new IllegalArgumentException("ID字节数组必须为16字节长度");
        }

        ByteBuffer buffer = ByteBuffer.wrap(idBytes).order(ByteOrder.BIG_ENDIAN);
        long mostSigBits = buffer.getLong();  // 最高64位
        long leastSigBits = buffer.getLong(); // 最低64位

        return new UUID(mostSigBits, leastSigBits);
    }

    /**
     * 生成下一个128位雪花ID
     *
     * @return 128位ID的字节数组形式（16字节）
     */
    public synchronized byte[] nextId() {
        long timestamp = System.currentTimeMillis();

        // 时钟回拨处理：若当前时间 < 上次时间，说明时钟回拨，抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("时钟回拨，拒绝生成ID，时间差：" + (lastTimestamp - timestamp) + "ms");
        }

        // 同一毫秒内，序列号自增
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 序列号溢出：等待至下一毫秒
            if (sequence == 0) {
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            // 新的毫秒，序列号重置为0
            sequence = 0L;
        }

        // 更新上次生成时间
        lastTimestamp = timestamp;

        // 计算128位ID（拆分为高64位和低64位）
        long deltaTime = timestamp - START_TIMESTAMP;

        // 高64位：0(符号位) + 41位时间戳 + 22位预留位(全0)
        // 确保时间戳不超过41位
        if (deltaTime >= (1L << TIMESTAMP_BITS)) {
            throw new RuntimeException("时间戳超出41位限制，系统需要更新");
        }
        long highBits = deltaTime << HIGH_RESERVED_BITS;

        // 低64位：10位数据中心ID + 10位机器ID + 44位序列号
        long lowBits = (dataCenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;

        // 将两个long转换为16字节数组，并明确指定大端序
        ByteBuffer buffer = ByteBuffer.allocate(16).order(ByteOrder.BIG_ENDIAN);
        buffer.putLong(highBits); // 高64位
        buffer.putLong(lowBits);  // 低64位
        return buffer.array();
    }

    /**
     * 等待至下一毫秒
     */
    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            // 使用更精确的等待机制，减少CPU占用
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    /**
     * 生成下一个128位雪花ID（UUID形式）
     *
     * @return UUID格式的128位ID
     */
    public synchronized UUID nextUuid() {
        byte[] idBytes = nextId();
        return toUUID(idBytes);
    }

}
