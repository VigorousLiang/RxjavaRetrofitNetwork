package com.vigorous.asynchronized.network;

import com.vigorous.asynchronized.network.api.HttpRequestService;
import com.vigorous.asynchronized.network.data.request.ExampleRequestParam;
import com.vigorous.asynchronized.network.data.response.ExampleEntity;
import com.vigorous.asynchronized.network.exception.AsyncHttpManagerNotInitException;
import com.vigorous.asynchronized.network.listener.AsyncRequestListener;
import com.vigorous.asynchronized.network.request.AsyncHttpManager;
import com.vigorous.asynchronized.network.request.AsyncSubscriberCreater;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Vigorous.Liang on 2017/9/18.
 */
public class AsyncNetWorkAPIFactory {

    private AsyncRequestListener mINetworkListener;
    private SoftReference<HttpRequestService> httpServiceWeakReference;
    private ConcurrentLinkedQueue<CompositeDisposable> mCancelableRequest;
    public final static int REQUEST_TYPE_CANCEL_WITH_ACTIVITY = 1;
    public final static int REQUEST_TYPE_BACKGROUND = REQUEST_TYPE_CANCEL_WITH_ACTIVITY
            + 1;

    public AsyncNetWorkAPIFactory(AsyncRequestListener l) {
        mINetworkListener = l;
        mCancelableRequest = new ConcurrentLinkedQueue<CompositeDisposable>();
        getHttpService();

    }

    private void getHttpService() {
        AsyncHttpManager asyncHttpManager = AsyncHttpManager.getInstance();
        try {
            httpServiceWeakReference = new SoftReference<HttpRequestService>(
                    asyncHttpManager.getNetWorkAPI());
        } catch (AsyncHttpManagerNotInitException e) {
            e.printStackTrace();
        }
    }

    public void cancelAllCancelableRequest() {
        Iterator<CompositeDisposable> iterator = mCancelableRequest.iterator();
        while (iterator.hasNext()) {
            mCancelableRequest.poll().clear();
        }
    }

    public void sendPostMessageForExample(int requestID, int type,
                                          ExampleRequestParam param) {
        if (httpServiceWeakReference.get() == null) {
            getHttpService();
        }
        if (param != null) {
            CompositeDisposable disposables = new CompositeDisposable();
            AsyncSubscriberCreater<ExampleEntity> asyncSubscriberCreater = new AsyncSubscriberCreater<ExampleEntity>();
            Observable<ExampleEntity> observable = httpServiceWeakReference.get().getAllVedio(param);
            disposables.add(observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(asyncSubscriberCreater.create(mINetworkListener, requestID)));
            if (type == REQUEST_TYPE_CANCEL_WITH_ACTIVITY) {
                mCancelableRequest.add(disposables);
            }
        }
    }
}