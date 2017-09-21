package com.vigorous.asynchronized.network.data.download;

/**
 * Created by Vigorous.Liang on 2017/9/19.
 */

public class DownloadInfo {

    public final static int DOWNLOAD_STATUS_START = 1;
    public final static int DOWNLOAD_STATUS_DOWNLOADING = DOWNLOAD_STATUS_START
            + 1;
    public final static int DOWNLOAD_STATUS_PAUSE = DOWNLOAD_STATUS_DOWNLOADING
            + 1;
    public final static int DOWNLOAD_STATUS_FINISH = DOWNLOAD_STATUS_PAUSE + 1;
    public final static int DOWNLOAD_STATUS_ERROR = DOWNLOAD_STATUS_FINISH + 1;

    /* 存储位置 */
    private String savePath;
    /* url */
    private String url;
    /* 文件总长度 */
    private long countLength;
    /* 下载长度 */
    private long readLength;
    /* 下载状态 */
    private int downloadStatus = DOWNLOAD_STATUS_START;

    public String getSavePath() {
        return savePath;
    }

    public String getUrl() {
        return url;
    }

    public long getCountLength() {
        return countLength;
    }

    public long getReadLength() {
        return readLength;
    }

    public int getDownloadStatus() {
        return downloadStatus;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public void setDownloadStatus(int downloadStatus) {
        this.downloadStatus = downloadStatus;
    }
}
