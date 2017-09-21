package com.vigorous.asynchronized.network.listener;

/**
 * Created by Vigorous.Liang on 2017/9/19.
 */

public interface AsyncUploadListener {
    /**
     * 开始下载
     */
     void onStart();

    /**
     * 完成下载
     */
     void onComplete();
    /**
     * 完成下载
     */
     void onError(Throwable t);
}
