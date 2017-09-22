package com.vigorous.asynchronized.network.request;

import android.text.TextUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.vigorous.asynchronized.network.api.HttpRequestService;
import com.vigorous.asynchronized.network.config.NetWorkRequestConfiguration;
import com.vigorous.asynchronized.network.exception.AsyncHttpManagerNotInitException;
import com.vigorous.asynchronized.network.https.TrustAllCerts;
import com.vigorous.asynchronized.network.https.TrustAllHostnameVerifier;
import com.vigorous.asynchronized.network.util.HttpLogger;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
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

    public void init(String baseUrl,
            NetWorkRequestConfiguration configuration) {
        if (TextUtils.isEmpty(baseUrl)) {
            return;
        }

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(
                new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(logInterceptor).connectTimeout(
                        configuration.getConnectionTime(), TimeUnit.SECONDS);

        // Deal with https request(trust all certificate here)
        if (baseUrl.startsWith("https") || baseUrl.startsWith("HTTPS")) {
            builder.sslSocketFactory(createSSLSocketFactory())
                    .hostnameVerifier(new TrustAllHostnameVerifier());
        }
        OkHttpClient client = builder.build();
        if (configuration.isEncrypted()) {
            mRetrofit = new Retrofit.Builder().client(client)
                    .addConverterFactory(
                            RequestEncryptConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl).build();
        } else {
            mRetrofit = new Retrofit.Builder().client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl).build();
        }
        isInit = true;
    }

    public boolean isInit() {
        return isInit;
    }

    public HttpRequestService getNetWorkAPI()
            throws AsyncHttpManagerNotInitException {
        if (mRetrofit == null) {
            throw new AsyncHttpManagerNotInitException();
        }
        return mRetrofit.create(HttpRequestService.class);
    }

    private SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[] { new TrustAllCerts() },
                    new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ssfFactory;
    }
}
