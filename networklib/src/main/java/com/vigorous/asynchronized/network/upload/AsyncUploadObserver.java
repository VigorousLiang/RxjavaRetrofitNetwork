package com.vigorous.asynchronized.network.upload;

import com.vigorous.asynchronized.network.listener.AsyncUploadListener;
import java.lang.ref.SoftReference;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Vigorous.Liang on 2017/9/19.
 */
public class AsyncUploadObserver<T> implements Observer<T> {

    // 弱引用结果回调
    private SoftReference<AsyncUploadListener> mUploadListener;

    private Disposable disposable;

    public AsyncUploadObserver(AsyncUploadListener asyncUploadListener) {
        this.mUploadListener = new SoftReference<>(asyncUploadListener);

    }

    public Disposable getDisposable() {
        return disposable;
    }

    public SoftReference<AsyncUploadListener> getCallback() {
        return mUploadListener;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        disposable = d;
        if (mUploadListener.get() != null) {
            mUploadListener.get().onStart();
        }
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable t) {
        if (mUploadListener.get() != null) {
            mUploadListener.get().onError(t);
        }
    }

    @Override
    public void onComplete() {
        if (mUploadListener.get() != null) {
            mUploadListener.get().onComplete();
        }
    }
}
