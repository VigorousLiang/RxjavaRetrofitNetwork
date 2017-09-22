package com.vigorous.asynchronized.network.listener;

/**
 * Created by Vigorous.Liang on 2017/9/19.
 */

public interface AsyncUploadListener {
    /**
     * 开始上传
     */
     void onStart();
    /**
     * 终止上传
     */
    void onStop();

    /**
     * 完成上传
     */
     void onComplete();
    /**
     * 上传失败
     */
     void onError(Throwable t);

    /**
     * 上传进度回调
     * @param progress
     * @param done
     */
    void update(double progress, boolean done);
}
