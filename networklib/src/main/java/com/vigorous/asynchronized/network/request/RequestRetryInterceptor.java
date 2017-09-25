package com.vigorous.asynchronized.network.request;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Vigorous.Liang on 2017/9/25.
 */

public class RequestRetryInterceptor implements Interceptor {
    // 重试上限次数
    private int maxRetryTimes;
    // 当前request的重试次数
    private int currentRetryTime = 0;

    public RequestRetryInterceptor(int maxRetry) {
        // 通过interceptor方式最大重试次数上限为3次
        this.maxRetryTimes = maxRetry > 3 ? 3 : maxRetry;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        while (!response.isSuccessful() && currentRetryTime < maxRetryTimes) {
            currentRetryTime++;
            response = chain.proceed(request);
        }
        return response;
    }
}
