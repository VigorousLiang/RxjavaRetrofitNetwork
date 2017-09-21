package com.vigorous.asynchronized.network.download;

import com.vigorous.asynchronized.network.data.download.DownloadResponseBody;
import com.vigorous.asynchronized.network.listener.AsyncDownloadProgressListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Vigorous.Liang on 2017/9/19.
 */

public class DownloadInterceptor implements Interceptor {

    private AsyncDownloadProgressListener listener;

    public DownloadInterceptor(AsyncDownloadProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        return originalResponse.newBuilder()
                .body(new DownloadResponseBody(originalResponse.body(), listener))
                .build();
    }
}
