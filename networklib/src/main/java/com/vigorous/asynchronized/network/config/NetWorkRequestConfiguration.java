package com.vigorous.asynchronized.network.config;

/**
 * Created by Vigorous.Liang on 2017/9/15.
 */

public class NetWorkRequestConfiguration {

    /* 是否需要加密 */
    private boolean isEncrypted = false;

    /* 超时时间-默认6秒 */
    private int connectionTime = 6;

    /* 是否需要缓存处理 */
    private boolean cache = false;
    /* 缓存区大小 */
    private int cacheSize = 10;
    /* 缓存数据生命周期长度（秒） */
    private int cacheAgeLimit = 10;

    /* 请求失败是否重试 */
    private boolean isRetry = false;
    /* 当isRetry为true时才生效，失败后retry次数 */
    private int retryCount = 1;
    /* 当isRetry为true时才生效，失败后retry延迟(毫秒) */
    private long retryDelay = 0;
    /* 当isRetry为true时才生效，失败后retry叠加延迟(毫秒) */
    private long retryIncreaseDelay = 0;

    public int getConnectionTime() {
        return connectionTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public long getRetryDelay() {
        return retryDelay;
    }

    public long getRetryIncreaseDelay() {
        return retryIncreaseDelay;
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }

    public boolean isCache() {
        return cache;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public int getCacheAgeLimit() {
        return cacheAgeLimit;
    }

    public boolean isRetry() {
        return isRetry;
    }

    public NetWorkRequestConfiguration setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
        return this;
    }

    public NetWorkRequestConfiguration setCache(boolean cache) {
        this.cache = cache;
        return this;
    }

    public NetWorkRequestConfiguration setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public NetWorkRequestConfiguration setRetryDelay(long retryDelay) {
        this.retryDelay = retryDelay;
        return this;
    }

    public NetWorkRequestConfiguration setRetryIncreaseDelay(
            long retryIncreaseDelay) {
        this.retryIncreaseDelay = retryIncreaseDelay;
        return this;
    }

    public NetWorkRequestConfiguration setEncrypted(boolean encrypted) {
        isEncrypted = encrypted;
        return this;
    }

    public NetWorkRequestConfiguration setRetry(boolean retry) {
        isRetry = retry;
        return this;
    }

    public NetWorkRequestConfiguration setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
        return this;
    }

    public NetWorkRequestConfiguration setCacheAgeLimit(int cacheAgeLimit) {
        this.cacheAgeLimit = cacheAgeLimit;
        return this;
    }
}
