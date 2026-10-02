package org.xzframework.data.test;

import org.junit.jupiter.api.Test;
import org.xzframework.data.id.SnowflakeIdGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SnowflakeIdGenerator的单元测试类
 */
public class SnowflakeIdGeneratorTest {

    /**
     * 测试基本的ID生成功能
     */
    @Test
    public void testBasicIdGeneration() {
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1);

        // 生成ID
        long id1 = generator.nextId();
        long id2 = generator.nextId();

        // 验证ID不为0
        assertNotEquals(0, id1);
        assertNotEquals(0, id2);

        // 验证两次生成的ID不同
        assertNotEquals(id1, id2);
    }

    /**
     * 测试参数验证
     */
    @Test
    public void testParameterValidation() {
        // 测试无效的机器ID
        assertThrows(IllegalArgumentException.class, () -> {
            new SnowflakeIdGenerator(-1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new SnowflakeIdGenerator(1024); // 超出最大值 2^10-1
        });

    }

    /**
     * 测试短时间内生成大量ID的唯一性
     */
    @Test
    public void testIdUniqueness() {
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1);
        Set<Long> idSet = new HashSet<>();
        int count = 1000000;

        for (int i = 0; i < count; i++) {
            long id = generator.nextId();
            assertFalse(idSet.contains(id), "生成了重复的ID: " + id);
            idSet.add(id);
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
        final SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1);
        final Set<Long> idSet = new HashSet<>();
        final CountDownLatch latch = new CountDownLatch(threadCount);
        final ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int t = 0; t < threadCount; t++) {
            executor.submit(() -> {
                try {
                    for (int i = 0; i < idsPerThread; i++) {
                        long id = generator.nextId();
                        synchronized (idSet) {
                            assertFalse(idSet.contains(id), "并发生成了重复的ID: " + id);
                            idSet.add(id);
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
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1);

        // 生成第一个ID
        long id1 = generator.nextId();

        // 等待一小段时间
        Thread.sleep(10);

        // 生成第二个ID
        long id2 = generator.nextId();

        // 由于时间戳不同，两个ID应该不同
        assertNotEquals(id1, id2);

        // 验证时间戳部分，确保ID随时间递增
        assertTrue(id2 > id1, "时间戳较晚的ID应该大于较早的ID");
    }

}
