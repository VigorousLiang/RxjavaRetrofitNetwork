package com.vigorous.asynchronized.network;

import android.content.Context;
import android.os.Environment;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.vigorous.asynchronized.network.api.FileUploadService;
import com.vigorous.asynchronized.network.config.NetWorkUploadConfiguration;
import com.vigorous.asynchronized.network.data.NetWorkRequestConst;
import com.vigorous.asynchronized.network.data.upload.UploadFileType;
import com.vigorous.asynchronized.network.data.upload.UploadInfo;
import com.vigorous.asynchronized.network.data.upload.UploadRequestBody;
import com.vigorous.asynchronized.network.exception.DownUploadWithoutWifiException;
import com.vigorous.asynchronized.network.exception.ExternalStorageReadPermissionException;
import com.vigorous.asynchronized.network.exception.UploadFileNotExistException;
import com.vigorous.asynchronized.network.exception.UploadParamInvaildException;
import com.vigorous.asynchronized.network.listener.AsyncUploadListener;
import com.vigorous.asynchronized.network.request.AsyncHttpManager;
import com.vigorous.asynchronized.network.upload.AsyncUploadObserver;
import com.vigorous.asynchronized.network.upload.UploadInterceptor;
import com.vigorous.asynchronized.network.util.AndroidPermissionUtil;
import com.vigorous.asynchronized.network.util.FileUtils;
import com.vigorous.asynchronized.network.util.NetworkStatusUtil;
import java.io.File;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vigorous.Liang on 2017/9/19.
 */

public class AsyncUploadManager {

    private volatile static AsyncUploadManager INSTANCE;
    private Context mContext;
    /* 记录下载数据 */
    private ConcurrentHashMap<String, SoftReference<AsyncUploadObserver>> uploadList;

    // 构造方法私有
    private AsyncUploadManager(Context context) {
        mContext = context.getApplicationContext();
        uploadList = new ConcurrentHashMap<String, SoftReference<AsyncUploadObserver>>();
    }

    // 获取单例
    public static AsyncUploadManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AsyncHttpManager.class) {
                if (INSTANCE == null && context != null) {
                    INSTANCE = new AsyncUploadManager(context);
                }
            }
        }
        return INSTANCE;
    }

    public void upload(UploadInfo uploadInfo, NetWorkUploadConfiguration config,
            AsyncUploadListener uploadListener) {
        // 判断下载配置参数是否为空
        if (config == null && uploadInfo != null) {
            if (uploadListener != null) {
                uploadListener.onError(new UploadParamInvaildException(
                        NetWorkRequestConst.UPLOAD_PARAM_INVALID));
            }
            return;
        }
        // 若文件保存地址为外部存储，判断是否有外部存储的读权限
        if (uploadInfo.getFilePath().contains(
                Environment.getExternalStorageDirectory().getAbsolutePath())) {
            if (!AndroidPermissionUtil.checkPermission(mContext,
                    AndroidPermissionUtil.CODE_READ_EXTERNAL_STORAGE)) {
                if (uploadListener != null) {
                    uploadListener
                            .onError(new ExternalStorageReadPermissionException(
                                    NetWorkRequestConst.EXTERNAL_STORAGE_READ_PERMISSION_INVALID));
                }
                return;
            }
        }

        // 判断文件是否已存在
        if (!FileUtils.isFileExist(uploadInfo.getFilePath())) {
            if (uploadListener != null) {
                uploadListener.onError(new UploadFileNotExistException(
                        NetWorkRequestConst.UPLOAD_FILE_NOT_EXIST));
            }
            return;
        }
        // 若用户不允许在无wifi状态下上载且当前网络状态非wifi
        if (!config.isContinueIfWifiUnavailable() && NetworkStatusUtil
                .getNetworkState(mContext) != NetworkStatusUtil.NETWORK_WIFI) {
            if (uploadListener != null) {
                uploadListener.onError(new DownUploadWithoutWifiException(
                        NetWorkRequestConst.WIFI_UNAVAILABLE));
            }
            return;
        }
        File file = new File(uploadInfo.getFilePath());
        RequestBody requestFile = RequestBody
                .create(MediaType.parse("multipart/form-data"), file);

        UploadRequestBody uploadRequestBody = new UploadRequestBody(requestFile,
                uploadListener);
        // 获取文件后缀名
        String prefix = uploadInfo.getFilePath()
                .substring(uploadInfo.getFilePath().lastIndexOf(".") + 1);

        // MultipartBody.Part
        MultipartBody.Part body = MultipartBody.Part.createFormData(
                UploadFileType.fileSuffixName2ContentType(prefix),
                file.getName(), uploadRequestBody);

        // 添加描述
        RequestBody description = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                uploadInfo.getFileDesc());

        AsyncUploadObserver<ResponseBody> asyncDownloadObserver = new AsyncUploadObserver<>(
                uploadListener);
        uploadList.put(uploadInfo.getFilePath(),
                new SoftReference<AsyncUploadObserver>(asyncDownloadObserver));

        UploadInterceptor uploadInterceptor = new UploadInterceptor();
        okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(config.getConnectionTime(), TimeUnit.SECONDS)
                .addInterceptor(uploadInterceptor);

        Retrofit retrofit = new Retrofit.Builder().client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(NetworkStatusUtil.getBasUrl(uploadInfo.getUrl()))
                .build();

        FileUploadService service = retrofit.create(FileUploadService.class);
        service.upload(uploadInfo.getUrl(), description, body)
                .subscribeOn(Schedulers.io())
                /* 回调线程 */
                .observeOn(AndroidSchedulers.mainThread())
                /* 数据回调 */
                .subscribe(asyncDownloadObserver);

    }

    /**
     * 取消上载
     */
    public boolean stopUpload(UploadInfo info) {
        if (info == null) {
            return false;
        }
        SoftReference<AsyncUploadObserver> softReference = uploadList
                .get(info.getFilePath());
        // Task not exist
        if (softReference == null) {
            return true;
        }
        AsyncUploadObserver currentObserver = softReference.get();
        if (currentObserver != null) {
            currentObserver.getDisposable().dispose();
            SoftReference<AsyncUploadListener> listenerSoftReference = currentObserver
                    .getCallback();
            if (listenerSoftReference != null) {
                listenerSoftReference.get().onStop();
            }
            uploadList.remove(info.getUrl());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 取消全部下载
     */
    public void stopAllDown() {
        Iterator<SoftReference<AsyncUploadObserver>> iterator = uploadList
                .values().iterator();
        while (iterator.hasNext()) {
            SoftReference<AsyncUploadObserver> asyncUploadObserverSoftReference = iterator
                    .next();
            if (asyncUploadObserverSoftReference.get() != null) {
                asyncUploadObserverSoftReference.get().getDisposable()
                        .dispose();
            }
        }
        uploadList.clear();
    }

}
