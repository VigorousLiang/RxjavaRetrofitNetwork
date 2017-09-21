package com.vigorous.asynchronized.network.config;

/**
 * Created by Vigorous.Liang on 2017/9/15.
 */

public class NetWorkRequestConfiguration {

    /*是否需要加密*/
    private boolean isEncrypted = false;
    /* 超时时间-默认6秒 */
    private int connectionTime = 6;
    /*是否需要缓存处理*/
    private boolean cache;
    /* 有网情况下的本地缓存时间默认60秒 */
    private int cookieNetWorkTime = 60;
    /* 无网络的情况下本地缓存时间默认30天 */
    private int cookieNoNetWorkTime = 24 * 60 * 60 * 30;
    /* 失败后retry次数 */
    private int retryCount = 1;
    /* 失败后retry延迟 */
    private long retryDelay = 100;
    /* 失败后retry叠加延迟 */
    private long retryIncreaseDelay = 10;

    public int getConnectionTime() {
        return connectionTime;
    }

    public int getCookieNetWorkTime() {
        return cookieNetWorkTime;
    }

    public int getCookieNoNetWorkTime() {
        return cookieNoNetWorkTime;
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

    public NetWorkRequestConfiguration setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
        return this;
    }

    public NetWorkRequestConfiguration setCache(boolean cache) {
        this.cache = cache;
        return this;
    }

    public NetWorkRequestConfiguration setCookieNetWorkTime(int cookieNetWorkTime) {
        this.cookieNetWorkTime = cookieNetWorkTime;
        return this;
    }

    public NetWorkRequestConfiguration setCookieNoNetWorkTime(
            int cookieNoNetWorkTime) {
        this.cookieNoNetWorkTime = cookieNoNetWorkTime;
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

    public NetWorkRequestConfiguration setRetryIncreaseDelay(long retryIncreaseDelay) {
        this.retryIncreaseDelay = retryIncreaseDelay;
        return this;
    }

    public NetWorkRequestConfiguration setEncrypted(boolean encrypted) {
        isEncrypted = encrypted;
        return this;
    }
}
