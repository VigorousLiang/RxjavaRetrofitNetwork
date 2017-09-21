package com.vigorous.rxjavaretrofitnetwork.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import com.vigorous.asynchronized.network.AsyncUploadManager;
import com.vigorous.asynchronized.network.config.NetWorkUploadConfiguration;
import com.vigorous.asynchronized.network.data.upload.UploadInfo;
import com.vigorous.asynchronized.network.listener.AsyncUploadListener;
import com.vigorous.rxjavaretrofitnetwork.R;
import com.vigorous.rxjavaretrofitnetwork.ui.base.BaseActivity;
import java.io.File;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UploadActivity extends BaseActivity {
    @BindView(R.id.btn_upload)
    Button mBtnUpload;
    @BindView(R.id.btn_upload_cancel)
    Button mBtnCancel;

    protected void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_upload);
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

    @OnClick(R.id.btn_upload_cancel)
    void cancelUpload() {
        File outputFile = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS),
                "test" + ".apk");
        UploadInfo uploadInfo = new UploadInfo();
        uploadInfo.setFilePath(outputFile.getAbsolutePath());
        uploadInfo.setFileDesc("APK");
        uploadInfo.setUrl(
                "http://www.izaodao.com/Api/AppFiftyToneGraph/videoLink");
        AsyncUploadManager asyncUploadManager = AsyncUploadManager
                .getInstance(getApplicationContext());
        asyncUploadManager.stopUpload(uploadInfo);
    }

    @OnClick(R.id.btn_upload)
    void toUpload() {
        File outputFile = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS),
                "aaa" + ".mp4");
        UploadInfo uploadInfo = new UploadInfo();
        uploadInfo.setFilePath(outputFile.getAbsolutePath());
        uploadInfo.setFileDesc("APK");
        uploadInfo.setUrl(
                "http://www.izaodao.com/Api/AppFiftyToneGraph/videoLink");
        AsyncUploadManager asyncUploadManager = AsyncUploadManager
                .getInstance(getApplicationContext());
        asyncUploadManager.upload(uploadInfo, new NetWorkUploadConfiguration(),
                new AsyncUploadListener() {
                    @Override
                    public void onStart() {
                        Log.e("upload", "start");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("upload", "complete");
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("upload", "error" + t.getMessage());
                    }
                });

    }
}
