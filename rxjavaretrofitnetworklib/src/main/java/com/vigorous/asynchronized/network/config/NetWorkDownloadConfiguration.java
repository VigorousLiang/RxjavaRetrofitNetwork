package com.vigorous.asynchronized.network.config;

/**
 * Created by Vigorous.Liang on 2017/9/15.
 */

public class NetWorkDownloadConfiguration {

    /* 超时时间-默认6秒 */
    private int connectionTime = 6;
    /* 失败后retry次数 */
    private int retryCount = 1;
    /* 失败后retry延迟(毫秒) */
    private long retryDelay = 100;
    /* 失败后retry叠加延迟（秒） */
    private long retryIncreaseDelay = 10;
    /* 若文件已存在是否覆盖 */
    private boolean rewriteIfFileExist = false;
    /* 若非WIFI网络连接是否继续下载 */
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

    public boolean isRewriteIfFileExist() {
        return rewriteIfFileExist;
    }

    public boolean isContinueIfWifiUnavailable() {
        return continueIfWifiUnavailable;
    }

    public NetWorkDownloadConfiguration setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
        return this;
    }

    public NetWorkDownloadConfiguration setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public NetWorkDownloadConfiguration setRetryDelay(long retryDelay) {
        this.retryDelay = retryDelay;
        return this;
    }

    public NetWorkDownloadConfiguration setRetryIncreaseDelay(
            long retryIncreaseDelay) {
        this.retryIncreaseDelay = retryIncreaseDelay;
        return this;
    }

    public NetWorkDownloadConfiguration setRewriteIfFileExist(
            boolean rewriteIfFileExist) {
        this.rewriteIfFileExist = rewriteIfFileExist;
        return this;
    }

    public NetWorkDownloadConfiguration setContinueIfWifiUnavailable(
            boolean continueIfWifiUnavailable) {
        this.continueIfWifiUnavailable = continueIfWifiUnavailable;
        return this;
    }
}
