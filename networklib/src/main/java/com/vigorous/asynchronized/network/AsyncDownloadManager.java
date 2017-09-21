package com.vigorous.asynchronized.network;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.vigorous.asynchronized.network.api.HttpDownloadService;
import com.vigorous.asynchronized.network.config.NetWorkDownloadConfiguration;
import com.vigorous.asynchronized.network.data.NetWorkRequestConst;
import com.vigorous.asynchronized.network.data.download.DownloadInfo;
import com.vigorous.asynchronized.network.download.AsyncDownloadObserver;
import com.vigorous.asynchronized.network.download.DownloadInterceptor;
import com.vigorous.asynchronized.network.exception.DownloadFileExistException;
import com.vigorous.asynchronized.network.exception.DownloadParamInvaildException;
import com.vigorous.asynchronized.network.exception.DownUploadWithoutWifiException;
import com.vigorous.asynchronized.network.exception.ExternalStorageWritePermissionException;
import com.vigorous.asynchronized.network.exception.HttpTimeException;
import com.vigorous.asynchronized.network.listener.AsyncDownloadProgressListener;
import com.vigorous.asynchronized.network.request.AsyncHttpManager;
import com.vigorous.asynchronized.network.util.AndroidPermissionUtil;
import com.vigorous.asynchronized.network.util.FileUtils;
import com.vigorous.asynchronized.network.util.NetworkUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.ref.SoftReference;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vigorous.Liang on 2017/9/19.
 */

public class AsyncDownloadManager {

    private volatile static AsyncDownloadManager INSTANCE;
    private Context mContext;
    /* 记录下载数据 */
    private ConcurrentHashMap<String, SoftReference<AsyncDownloadObserver>> downloadList;

    // 构造方法私有
    private AsyncDownloadManager(Context context) {
        mContext = context.getApplicationContext();
        downloadList = new ConcurrentHashMap<String, SoftReference<AsyncDownloadObserver>>();
    }

    // 获取单例
    public static AsyncDownloadManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AsyncHttpManager.class) {
                if (INSTANCE == null && context != null) {
                    INSTANCE = new AsyncDownloadManager(context);
                }
            }
        }
        return INSTANCE;
    }

    public void startDownload(NetWorkDownloadConfiguration configuration,
            final DownloadInfo downloadInfo,
            AsyncDownloadProgressListener listener) {
        // 判断下载配置参数是否为空 TODO 判断下载url是否合法
        if (configuration == null || downloadInfo == null
                || TextUtils.isEmpty(downloadInfo.getUrl())
                || TextUtils.isEmpty(downloadInfo.getSavePath())) {
            if (listener != null) {
                listener.onError(new DownloadParamInvaildException(
                        NetWorkRequestConst.DOWNLOAD_PARAM_INVALID));
            }
            return;
        }
        // 若文件保存地址为外部存储，判断是否有外部存储的写权限
        if (downloadInfo.getSavePath().contains(
                Environment.getExternalStorageDirectory().getAbsolutePath())) {
            if (!AndroidPermissionUtil.checkPermission(mContext,
                    AndroidPermissionUtil.CODE_WRITE_EXTERNAL_STORAGE)) {
                if (listener != null) {
                    listener.onError(
                            new ExternalStorageWritePermissionException(
                                    NetWorkRequestConst.EXTERNAL_STORAGE_WRITE_PERMISSION_INVALID));
                }
                return;
            }
        }

        // 判断文件是否已存在且用户不允许覆盖
        if (FileUtils.isFileExist(downloadInfo.getSavePath())
                && !configuration.isRewriteIfFileExist()) {
            if (listener != null) {
                listener.onError(new DownloadFileExistException(
                        NetWorkRequestConst.DOWNLOAD_FILE_ALREADY_EXIST));
            }
            return;
        }
        // 若用户不允许在无wifi状态下下载且当前网络状态非wifi
        if (!configuration.isContinueIfWifiUnavailable() && NetworkUtil
                .getNetworkState(mContext) != NetworkUtil.NETWORK_WIFI) {
            if (listener != null) {
                listener.onError(new DownUploadWithoutWifiException(
                        NetWorkRequestConst.WIFI_UNAVAILABLE));
            }
            return;
        }
        AsyncDownloadObserver<DownloadInfo> asyncDownloadObserver = new AsyncDownloadObserver<>(
                downloadInfo, listener);
        DownloadInterceptor interceptor = new DownloadInterceptor(listener);
        okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(configuration.getConnectionTime(),
                        TimeUnit.SECONDS)
                .addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder().client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(NetworkUtil.getBasUrl(downloadInfo.getUrl())).build();

        HttpDownloadService service = retrofit
                .create(HttpDownloadService.class);

        service.download("bytes=" + downloadInfo.getReadLength() + "-",
                downloadInfo.getUrl())
                /* 指定线程 */
                .subscribeOn(Schedulers.io())
                /* 读取下载写入文件 */
                .map(new Function<ResponseBody, DownloadInfo>() {
                    @Override
                    public DownloadInfo apply(ResponseBody responseBody) {
                        writeCaches(responseBody,
                                new File(downloadInfo.getSavePath()),
                                downloadInfo);
                        return downloadInfo;
                    }
                })
                /* 回调线程 */
                .observeOn(AndroidSchedulers.mainThread())
                /* 数据回调 */
                .subscribe(asyncDownloadObserver);
        downloadList.put(downloadInfo.getUrl(),
                new SoftReference<AsyncDownloadObserver>(
                        asyncDownloadObserver));
    }

    /**
     * 取消下载
     */
    public boolean stopDown(DownloadInfo info) {
        if (info == null) {
            return false;
        }
        AsyncDownloadObserver currentObserver = downloadList.get(info.getUrl())
                .get();
        if (currentObserver != null) {
            currentObserver.getDisposable().dispose();
            downloadList.remove(info.getUrl());
            FileUtils.deleteFile(info.getSavePath());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 取消全部下载
     */
    public void stopAllDown() {
        Iterator<SoftReference<AsyncDownloadObserver>> iterator = downloadList
                .values().iterator();
        while (iterator.hasNext()) {
            SoftReference<AsyncDownloadObserver> asyncDownloadObserverSoftReference = iterator
                    .next();
            if (asyncDownloadObserverSoftReference.get() != null) {
                asyncDownloadObserverSoftReference.get().getDisposable()
                        .dispose();
                FileUtils.deleteFile(asyncDownloadObserverSoftReference.get()
                        .getDownInfo().getSavePath());
            }
        }
        downloadList.clear();
    }

    /**
     * 写入文件
     *
     * @param file
     * @param info
     * @throws IOException
     */
    private void writeCaches(ResponseBody responseBody, File file,
            DownloadInfo info) {
        try {
            RandomAccessFile randomAccessFile = null;
            FileChannel channelOut = null;
            InputStream inputStream = null;
            try {
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                long allLength = 0 == info.getCountLength()
                        ? responseBody.contentLength()
                        : info.getReadLength() + responseBody.contentLength();

                inputStream = responseBody.byteStream();
                randomAccessFile = new RandomAccessFile(file, "rwd");
                channelOut = randomAccessFile.getChannel();
                MappedByteBuffer mappedBuffer = channelOut.map(
                        FileChannel.MapMode.READ_WRITE, info.getReadLength(),
                        allLength - info.getReadLength());
                byte[] buffer = new byte[1024 * 4];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    mappedBuffer.put(buffer, 0, len);
                }
            } catch (IOException e) {
                throw new HttpTimeException(e.getMessage());
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (channelOut != null) {
                    channelOut.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            }
        } catch (IOException e) {
            throw new HttpTimeException(e.getMessage());
        }
    }

}
