package com.vigorous.asynchronized.network.config;

/**
 * Created by Vigorous.Liang on 2017/9/15.
 */

public class NetWorkUploadConfiguration {

    /* 超时时间-默认6秒 */
    private int connectionTime = 6;
    /* 失败后retry次数 */
    private int retryCount = 1;
    /* 失败后retry延迟(毫秒) */
    private long retryDelay = 100;
    /* 失败后retry叠加延迟（秒） */
    private long retryIncreaseDelay = 10;
    /* 若非WIFI网络连接是否继续上传 */
    private boolean continueIfWifiUnavailable = false;

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

    public boolean isContinueIfWifiUnavailable() {
        return continueIfWifiUnavailable;
    }

    public NetWorkUploadConfiguration setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
        return this;
    }

    public NetWorkUploadConfiguration setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public NetWorkUploadConfiguration setRetryDelay(long retryDelay) {
        this.retryDelay = retryDelay;
        return this;
    }

    public NetWorkUploadConfiguration setRetryIncreaseDelay(
            long retryIncreaseDelay) {
        this.retryIncreaseDelay = retryIncreaseDelay;
        return this;
    }

    public NetWorkUploadConfiguration setContinueIfWifiUnavailable(
            boolean continueIfWifiUnavailable) {
        this.continueIfWifiUnavailable = continueIfWifiUnavailable;
        return this;
    }
}
