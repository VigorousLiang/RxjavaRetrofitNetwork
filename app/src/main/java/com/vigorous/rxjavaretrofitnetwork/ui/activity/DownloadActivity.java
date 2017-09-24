package com.vigorous.rxjavaretrofitnetwork.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import com.vigorous.asynchronized.network.AsyncDownloadManager;
import com.vigorous.asynchronized.network.config.NetWorkDownloadConfiguration;
import com.vigorous.asynchronized.network.data.download.DownloadInfo;
import com.vigorous.asynchronized.network.exception.ExternalStorageWritePermissionException;
import com.vigorous.asynchronized.network.listener.AsyncDownloadProgressListener;
import com.vigorous.asynchronized.network.util.AndroidPermissionUtil;
import com.vigorous.rxjavaretrofitnetwork.R;
import com.vigorous.rxjavaretrofitnetwork.ui.base.BaseActivity;
import java.io.File;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownloadActivity extends BaseActivity {
    @BindView(R.id.btn_download)
    Button mBtnDownload;
    @BindView(R.id.btn_download_cancel)
    Button mBtnCancel;

    protected void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_download);
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

        //设置下载信息
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.setUrl("http://www.izaodao.com/apk/wangxiao_v2.9.2.apk");
        File outputFile = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS),
                "test" + ".apk");
        downloadInfo.setSavePath(outputFile.getAbsolutePath());
        //设置下载配置信息
        NetWorkDownloadConfiguration config = new NetWorkDownloadConfiguration();
        config.setRewriteIfFileExist(true);

        AsyncDownloadManager asyncDownloadManager = AsyncDownloadManager
                .getInstance(getApplicationContext());
        asyncDownloadManager.startDownload(config, downloadInfo,
                new AsyncDownloadProgressListener() {
                    @Override
                    public void onStart() {
                        Log.e("download", "start");
                    }

                    @Override
                    public void onStop() {
                        Log.e("download", "stop");
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
                                    DownloadActivity.this,
                                    AndroidPermissionUtil.CODE_WRITE_EXTERNAL_STORAGE);
                        }
                    }

                    @Override
                    public void update(double progress, boolean done) {
                        Log.e("download", "downloading..." + progress);
                    }
                });
    }
}
