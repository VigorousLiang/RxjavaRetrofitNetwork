package com.vigorous.rxjavaretrofitnetwork.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import com.vigorous.asynchronized.network.AsyncDownloadManager;
import com.vigorous.asynchronized.network.AsyncNetWorkAPIFactory;
import com.vigorous.asynchronized.network.config.NetWorkDownloadConfiguration;
import com.vigorous.asynchronized.network.data.NetWorkRequestConst;
import com.vigorous.asynchronized.network.data.RequestId;
import com.vigorous.asynchronized.network.data.RespParamBase;
import com.vigorous.asynchronized.network.data.download.DownloadInfo;
import com.vigorous.asynchronized.network.data.request.ExampleRequestParam;
import com.vigorous.asynchronized.network.data.response.ExampleEntity;
import com.vigorous.asynchronized.network.data.response.Subject;
import com.vigorous.asynchronized.network.exception.ExternalStorageWritePermissionException;
import com.vigorous.asynchronized.network.listener.AsyncDownloadProgressListener;
import com.vigorous.asynchronized.network.util.AndroidPermissionUtil;
import com.vigorous.rxjavaretrofitnetwork.R;
import com.vigorous.rxjavaretrofitnetwork.ui.base.BaseActivity;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.btn_example)
    Button mBtnExample;
    @BindView(R.id.btn_download)
    Button mBtnDownload;
    @BindView(R.id.btn_download_cancel)
    Button mBtnCancel;

    protected void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_main);
    }

    // 启动顺序 1
    protected void findView() {
        super.findView();
    }

    // 启动顺序 2
    protected void initIntent(Intent intent) {
        super.initIntent(intent);
    }

    // 启动顺序 3
    protected void initView() {
        super.initView();
    }

    public void onCreateAfterSuper(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    public void onDestroyBeforeSuper() {

    }

    public void onDestroyAfterSuper() {

    }

    @OnClick(R.id.btn_example)
    void example() {
        sendPostRequestExample();
    }

    @OnClick(R.id.btn_download_cancel)
    void cancelDownload() {
        AsyncDownloadManager asyncDownloadManager = AsyncDownloadManager
                .getInstance(getApplicationContext());
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.setUrl("http://www.izaodao.com/apk/wangxiao_v2.9.2.apk");
        File outputFile = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS),
                "test" + ".apk");
        downloadInfo.setSavePath(outputFile.getAbsolutePath());
        asyncDownloadManager.stopDown(downloadInfo);
    }

    @OnClick(R.id.btn_download)
    void toDownload() {
        AsyncDownloadManager asyncDownloadManager = AsyncDownloadManager
                .getInstance(getApplicationContext());
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.setUrl("http://www.izaodao.com/apk/wangxiao_v2.9.2.apk");
        File outputFile = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS),
                "test" + ".apk");
        downloadInfo.setSavePath(outputFile.getAbsolutePath());
        asyncDownloadManager.startDownload(new NetWorkDownloadConfiguration(),
                downloadInfo, new AsyncDownloadProgressListener() {
                    @Override
                    public void onStart() {
                        Log.e("download", "start");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("download", "complete");
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("download", "error" + t.getMessage());
                        if (t instanceof ExternalStorageWritePermissionException) {
                            AndroidPermissionUtil.requestPermission(
                                    MainActivity.this,
                                    AndroidPermissionUtil.CODE_WRITE_EXTERNAL_STORAGE);
                        }
                    }

                    @Override
                    public void update(double progress, boolean done) {
                        Log.e("download", "downloading..." + progress);
                    }
                });
    }

    /**
     * network request success callback
     * 
     * @param requestID
     * @param baseResponse
     */
    @Override
    public void onResult(RequestId requestID, RespParamBase baseResponse) {
        switch (requestID.getID()) {
        case NetWorkRequestConst.REQUEST_EXAMPLE:
            Log.e("http", "Success");
            onExampleResult(baseResponse);
            break;
        }
    }

    private void onExampleResult(RespParamBase baseResponse) {
        if (baseResponse instanceof ExampleEntity) {
            ExampleEntity resp = (ExampleEntity) baseResponse;
            if (resp != null) {
                List<Subject> data = resp.getData();
                Iterator<Subject> iterator = data.iterator();
                while (iterator.hasNext()) {
                    Subject current = iterator.next();
                    Log.e("http", current.getTitle());
                }
            }
        }
    }

    /**
     * network request fail callback
     * 
     * @param requestID
     * @param errorCode
     * @param errorDesc
     */
    @Override
    public void onError(RequestId requestID, String errorCode,
            String errorDesc) {
        switch (requestID.getID()) {
        case NetWorkRequestConst.REQUEST_EXAMPLE:
            Log.e("http", "Error" + errorCode + " " + errorDesc);
            break;
        }
    }

    private void sendPostRequestExample() {
        Log.e("http", "StartRequest");
        ExampleRequestParam exampleRequestParam = new ExampleRequestParam();
        exampleRequestParam.setOnce_no(true);
        getAPIFactory().sendPostMessageForExample(
                NetWorkRequestConst.REQUEST_EXAMPLE,
                AsyncNetWorkAPIFactory.REQUEST_TYPE_CANCEL_WITH_ACTIVITY,
                exampleRequestParam);
    }
}
