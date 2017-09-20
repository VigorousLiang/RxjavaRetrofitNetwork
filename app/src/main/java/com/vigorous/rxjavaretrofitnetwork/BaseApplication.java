package com.vigorous.rxjavaretrofitnetwork;

import android.app.Application;

import com.vigorous.asynchronized.network.request.AsyncHttpManager;
import com.vigorous.asynchronized.network.config.NetWorkRequestConfiguration;
import com.vigorous.asynchronized.network.util.LogUtil;

/**
 * Created by Vigorous.Liang on 2017/9/15.
 */

public class BaseApplication extends Application {

    private final static String TAG = BaseApplication.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        //Debug 状态
        LogUtil.init(true);
        initNetWork();
    }

    @Override
    public void onTerminate() {
        // 注销这个接口。
        super.onTerminate();
    }

    private void initNetWork() {
        AsyncHttpManager asyncHttpManager = AsyncHttpManager.getInstance();
        // 设置网络配置参数，若不指定其内有默认参数，亦可
        NetWorkRequestConfiguration netWorkRequestConfiguration = new NetWorkRequestConfiguration();
        asyncHttpManager.init("http://www.izaodao.com/Api/",
                netWorkRequestConfiguration);
    }
}
