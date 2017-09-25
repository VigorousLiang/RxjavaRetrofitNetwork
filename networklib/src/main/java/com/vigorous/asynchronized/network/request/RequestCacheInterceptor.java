package com.vigorous.asynchronized.network.request;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Vigorous.Liang on 2017/9/25.
 */

public class RequestCacheInterceptor implements Interceptor {
    /*
     * 缓存有效期
     */
    private int cacheAgeLimit;

    public RequestCacheInterceptor(int cacheAgeLimit) {
        this.cacheAgeLimit = cacheAgeLimit;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originResponse = chain.proceed(chain.request());
        if (cacheAgeLimit > 0) {
            // 设置缓存时间为60秒，并移除了pragma消息头，移除它的原因是因为pragma也是控制缓存的一个消息头属性
            return originResponse.newBuilder().removeHeader("pragma")
                    .header("Cache-Control", "max-age=" + cacheAgeLimit)
                    .build();
        } else {
            // 若设置的缓存生命周期长度非法，则忽略设置，返回服务器原生response
            return originResponse;
        }
    }
}
