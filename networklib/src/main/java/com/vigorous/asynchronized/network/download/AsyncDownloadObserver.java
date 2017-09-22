package com.vigorous.asynchronized.network.download;

import com.vigorous.asynchronized.network.data.download.DownloadInfo;
import com.vigorous.asynchronized.network.listener.AsyncDownloadProgressListener;
import java.lang.ref.SoftReference;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Vigorous.Liang on 2017/9/19.
 */
public class AsyncDownloadObserver<T> implements Observer<T> {

    // 弱引用结果回调
    private SoftReference<AsyncDownloadProgressListener> mSubscriberOnNextListener;
    /* 下载数据 */
    private DownloadInfo downInfo;

    private Disposable disposable;

    public AsyncDownloadObserver(DownloadInfo downInfo,
            AsyncDownloadProgressListener asyncDownloadProgressListener) {
        this.mSubscriberOnNextListener = new SoftReference<>(
                asyncDownloadProgressListener);
        this.downInfo = downInfo;
    }

    public DownloadInfo getDownInfo() {
        return downInfo;
    }

    public Disposable getDisposable() {
        return disposable;
    }

    public SoftReference<AsyncDownloadProgressListener> getCallback() {
        return mSubscriberOnNextListener;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        disposable = d;
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onStart();
        }
        downInfo.setDownloadStatus(DownloadInfo.DOWNLOAD_STATUS_START);
    }

    @Override
    public void onNext(T t) {
        if (t instanceof DownloadInfo) {
            downInfo = (DownloadInfo) t;
            if (mSubscriberOnNextListener.get() != null) {
                mSubscriberOnNextListener.get().update(
                        downInfo.getCountLength() != 0
                                ? (downInfo.getReadLength()
                                        / downInfo.getCountLength()) * 100
                                : 100,
                        downInfo.getReadLength() == downInfo.getCountLength());
            }
            downInfo.setDownloadStatus(
                    DownloadInfo.DOWNLOAD_STATUS_DOWNLOADING);
        }
    }

    @Override
    public void onError(Throwable t) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onError(t);
        }
        downInfo.setDownloadStatus(DownloadInfo.DOWNLOAD_STATUS_ERROR);
    }

    @Override
    public void onComplete() {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onComplete();
        }
        downInfo.setDownloadStatus(DownloadInfo.DOWNLOAD_STATUS_FINISH);
    }
}
