package com.vigorous.asynchronized.network.request;

import com.vigorous.asynchronized.network.data.NetWorkRequestConst;
import com.vigorous.asynchronized.network.data.RequestId;
import com.vigorous.asynchronized.network.data.RespParamBase;
import com.vigorous.asynchronized.network.listener.AsyncRequestListener;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by Vigorous.Liang on 2017/9/18.
 */

public class AsyncSubscriberCreater<T extends RespParamBase> {

    public DisposableObserver<T> create(final AsyncRequestListener listener,
                                        int requestID) {
        final RequestId requestId = new RequestId(requestID);
        DisposableObserver subscriber = new DisposableObserver<T>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                listener.onError(requestId,
                        NetWorkRequestConst.RESPONSE_TIME_OUT,
                        NetWorkRequestConst.RESPONSE_TIME_OUT_STR);
            }

            @Override
            public void onNext(T t) {
                if (listener != null && t != null) {
                    if (t.getRespCode()
                            .equals(NetWorkRequestConst.RESPONSE_NORMAL)) {
                        listener.onResult(requestId, t);
                    } else {
                        listener.onError(requestId, t.getRespCode(),
                                t.getDesc());
                    }
                }
            }
        };
        return subscriber;
    }
}
