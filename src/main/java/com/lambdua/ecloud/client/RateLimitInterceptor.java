package com.lambdua.ecloud.client;

import okhttp3.Interceptor;
import okhttp3.Response;
import retrofit2.Invocation;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LiangTao
 * @date 2024年06月11 11:09
 **/
public class RateLimitInterceptor implements Interceptor {

    private final ConcurrentHashMap<String, RateLimiter> rateLimiters = new ConcurrentHashMap<>();
    private final String token;

    private final Random random = new Random();

    public RateLimitInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        RateLimit rateLimit = getRateLimitAnnotation(chain);
        if (rateLimit == null) {
            return chain.proceed(chain.request());
        }
        String key = token + ":" + rateLimit.type();
        rateLimiters.putIfAbsent(key, getPermitsFromAnnotation(rateLimit));
        RateLimiter rateLimiter = rateLimiters.get(key);

        if (rateLimit.timeout() == -1) {
            rateLimiter.acquire();
            randomSleep(rateLimit);
            return chain.proceed(chain.request());
        } else {
            if (rateLimiter.tryAcquire(Duration.ofSeconds(rateLimit.timeout()))) {
                randomSleep(rateLimit);
                return chain.proceed(chain.request());
            } else {
                throw new RateLimitException("Rate limit exceeded, please try again later");
            }
        }
    }


    private void randomSleep(RateLimit rateLimit) {
        String delaySplit = rateLimit.delay();
        if (delaySplit == null || delaySplit.isEmpty()) {
            return;
        }
        String[] split = delaySplit.split(",");
        // 随机延迟单位ms,在delaySplit[0]和delaySplit[1]之间
        int minDelay = Integer.parseInt(split[0].trim());
        int maxDelay = Integer.parseInt(split[1].trim());

        // 生成随机延迟
        int delay = minDelay + random.nextInt(maxDelay - minDelay + 1);
        try {
            // 休眠指定的时间
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RateLimitException("random sleep error");
        }
    }


    private RateLimiter getPermitsFromAnnotation(RateLimit rateLimit) {
        return new RateLimiter(rateLimit.limit(), Duration.ofSeconds(rateLimit.limitTime()));
    }

    private RateLimit getRateLimitAnnotation(Chain chain) {
        try {
            Method method = getMethod(chain);
            return method.getAnnotation(RateLimit.class);
        } catch (Exception e) {
            return null;
        }
    }

    private Method getMethod(Chain chain) {
        Invocation invocation = chain.request().tag(Invocation.class);
        if (invocation == null) {
            throw new RateLimitException("get method error");
        }
        return invocation.method();
    }

    public static class RateLimitException extends RuntimeException {
        public RateLimitException(String message) {
            super(message);
        }
    }
}
