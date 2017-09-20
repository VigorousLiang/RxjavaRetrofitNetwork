package com.vigorous.asynchronized.network.request;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.vigorous.asynchronized.network.api.HttpRequestService;
import com.vigorous.asynchronized.network.config.NetWorkRequestConfiguration;
import com.vigorous.asynchronized.network.exception.AsyncHttpManagerNotInitException;
import com.vigorous.asynchronized.network.util.HttpLogger;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vigorous.Liang on 2017/9/15.
 */

public class AsyncHttpManager {
    private volatile static AsyncHttpManager INSTANCE;
    private volatile boolean isInit = false;
    private Retrofit mRetrofit;

    // 构造方法私有
    private AsyncHttpManager() {
    }

    // 获取单例
    public static AsyncHttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (AsyncHttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AsyncHttpManager();
                }
            }
        }
        return INSTANCE;
    }

    public void init(String baseUrl) {
        init(baseUrl, new NetWorkRequestConfiguration());
    }

    public void init(String baseUrl, NetWorkRequestConfiguration configuration) {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(
                new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(logInterceptor).connectTimeout(
                        configuration.getConnectionTime(), TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder().client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl).build();
        isInit = true;
    }

    public boolean isInit() {
        return isInit;
    }

    public HttpRequestService getNetWorkAPI() throws AsyncHttpManagerNotInitException {
        if (mRetrofit == null) {
            throw new AsyncHttpManagerNotInitException();
        }
        return mRetrofit.create(HttpRequestService.class);
    }
}
