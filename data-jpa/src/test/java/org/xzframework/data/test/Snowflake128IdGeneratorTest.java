package org.xzframework.data.test;

import org.junit.jupiter.api.Test;
import org.xzframework.data.id.Snowflake128IdGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Snowflake128IdGenerator的单元测试类
 */
public class Snowflake128IdGeneratorTest {

    /**
     * 测试基本的ID生成功能
     */
    @Test
    public void testBasicIdGeneration() {
        Snowflake128IdGenerator generator = new Snowflake128IdGenerator(1, 1);

        // 生成ID
        byte[] id1 = generator.nextId();
        byte[] id2 = generator.nextId();

        // 验证ID不为空且长度为16字节
        assertNotNull(id1);
        assertNotNull(id2);
        assertEquals(16, id1.length);
        assertEquals(16, id2.length);

        // 验证两次生成的ID不同
        assertNotEquals(convertByteArrayToString(id1), convertByteArrayToString(id2));
    }

    /**
     * 测试UUID格式的ID生成
     */
    @Test
    public void testUuidGeneration() {
        Snowflake128IdGenerator generator = new Snowflake128IdGenerator(1, 1);

        // 生成UUID
        UUID uuid1 = generator.nextUuid();
        UUID uuid2 = generator.nextUuid();

        // 验证UUID不为空且不同
        assertNotNull(uuid1);
        assertNotNull(uuid2);
        assertNotEquals(uuid1, uuid2);
    }

    /**
     * 测试字节数组转UUID功能
     */
    @Test
    public void testBytesToUuid() {
        Snowflake128IdGenerator generator = new Snowflake128IdGenerator(1, 1);

        // 生成ID字节数组
        byte[] idBytes = generator.nextId();

        // 转换为UUID
        UUID uuid = Snowflake128IdGenerator.toUUID(idBytes);

        // 验证UUID不为空
        assertNotNull(uuid);
    }

    /**
     * 测试参数验证
     */
    @Test
    public void testParameterValidation() {
        // 测试无效的数据中心ID
        assertThrows(IllegalArgumentException.class, () -> {
            new Snowflake128IdGenerator(-1, 1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Snowflake128IdGenerator(65536, 1); // 超出最大值 2^16-1
        });

        // 测试无效的机器ID
        assertThrows(IllegalArgumentException.class, () -> {
            new Snowflake128IdGenerator(1, -1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Snowflake128IdGenerator(1, 65536); // 超出最大值 2^16-1
        });

    }

    /**
     * 测试短时间内生成大量ID的唯一性
     */
    @Test
    public void testIdUniqueness() {
        Snowflake128IdGenerator generator = new Snowflake128IdGenerator(1, 1);
        Set<String> idSet = new HashSet<>();
        int count = 10000;

        for (int i = 0; i < count; i++) {
            byte[] id = generator.nextId();
            String idStr = convertByteArrayToString(id);
            assertFalse(idSet.contains(idStr), "生成了重复的ID: " + idStr);
            idSet.add(idStr);
        }

        assertEquals(count, idSet.size(), "生成的ID数量不正确");
    }

    /**
     * 测试并发生成ID的唯一性
     */
    @Test
    public void testConcurrentIdGeneration() throws InterruptedException {
        final int threadCount = 100;
        final int idsPerThread = 10000;
        final Snowflake128IdGenerator generator = new Snowflake128IdGenerator(1, 1);
        final Set<String> idSet = new HashSet<>();
        final CountDownLatch latch = new CountDownLatch(threadCount);
        final ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int t = 0; t < threadCount; t++) {
            executor.submit(() -> {
                try {
                    for (int i = 0; i < idsPerThread; i++) {
                        byte[] id = generator.nextId();
                        synchronized (idSet) {
                            String idStr = convertByteArrayToString(id);
                            assertFalse(idSet.contains(idStr), "并发生成了重复的ID: " + idStr);
                            idSet.add(idStr);
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        // 等待所有线程完成
        latch.await();
        executor.shutdown();

        // 验证所有ID都唯一
        assertEquals(threadCount * idsPerThread, idSet.size(), "并发生成的ID总数不正确");
    }

    /**
     * 测试ID结构（验证时间戳部分）
     */
    @Test
    public void testIdStructure() throws InterruptedException {
        Snowflake128IdGenerator generator = new Snowflake128IdGenerator(1, 1);

        // 生成第一个ID
        byte[] id1 = generator.nextId();

        // 等待一小段时间
        Thread.sleep(10);

        // 生成第二个ID
        byte[] id2 = generator.nextId();

        // 由于时间戳不同，两个ID的前几个字节应该不同
        boolean different = false;
        for (int i = 0; i < 8; i++) { // 比较高64位的前8字节
            if (id1[i] != id2[i]) {
                different = true;
                break;
            }
        }
        assertTrue(different, "时间戳不同的ID应该不同");
    }

    /**
     * 辅助方法：将字节数组转换为字符串，用于比较
     */
    private String convertByteArrayToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

}
