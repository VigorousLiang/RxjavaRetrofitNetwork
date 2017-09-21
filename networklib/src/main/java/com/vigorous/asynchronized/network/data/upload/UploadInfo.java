package com.vigorous.asynchronized.network.data.upload;

/**
 * Created by Vigorous.Liang on 2017/9/19.
 */

public class UploadInfo {

    /*
     * 存储位置
     */
    private String filePath;
    /*
     * url
     */
    private String url;
    /*
     * 文件描述
     */
    private String fileDesc;

    public String getFilePath() {
        return filePath;
    }

    public String getUrl() {
        return url;
    }

    public String getFileDesc() {
        return fileDesc;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }
}
