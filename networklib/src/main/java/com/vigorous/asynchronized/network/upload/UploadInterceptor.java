package com.vigorous.asynchronized.network.upload;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Vigorous.Liang on 2017/9/21.
 */

public class UploadInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request currentRequest = chain.request().newBuilder()
                .addHeader("Connection", "close").build();
        return chain.proceed(currentRequest);
    }
}
