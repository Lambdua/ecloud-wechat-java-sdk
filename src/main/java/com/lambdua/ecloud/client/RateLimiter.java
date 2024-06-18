package com.lambdua.ecloud.client;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟真人操作的速率限制
 *
 * @author LiangTao
 * @date 2024年06月11 13:45
 **/
public class RateLimiter {
    private final int limit;
    private final Duration duration;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition tokensAvailable = lock.newCondition();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private int tokens;

    /**
     * @param limit    指定单位时间内可允许的请求数
     * @param duration 指定单位时间
     */
    public RateLimiter(int limit, Duration duration) {
        this.limit = limit;
        this.duration = duration;
        this.tokens = 0;
        // 初始化定时任务，向桶中添加令牌
        scheduler.scheduleAtFixedRate(this::addToken, 0, duration.toMillis() / limit, TimeUnit.MILLISECONDS);
    }

    private void addToken() {
        lock.lock();
        try {
            if (tokens < limit) {
                tokens++;
                tokensAvailable.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean acquire() {
        lock.lock();
        try {
            while (tokens <= 0) {
                tokensAvailable.await();
            }
            tokens--;
            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public boolean tryAcquire(Duration timeout) {
        lock.lock();
        try {
            long nanosToWait = timeout.toNanos();
            while (tokens <= 0) {
                if (nanosToWait <= 0) {
                    return false;
                }
                nanosToWait = tokensAvailable.awaitNanos(nanosToWait);
            }
            tokens--;
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            lock.unlock();
        }
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}

