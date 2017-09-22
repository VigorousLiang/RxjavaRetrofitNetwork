package com.vigorous.rxjavaretrofitnetwork.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.vigorous.asynchronized.network.AsyncNetWorkAPIFactory;
import com.vigorous.asynchronized.network.data.RequestId;
import com.vigorous.asynchronized.network.data.RespParamBase;
import com.vigorous.asynchronized.network.listener.AsyncRequestListener;

/**
 * Created by Vigorous.Liang on 2017/9/18.
 */

public abstract class BaseActivity extends FragmentActivity
        implements AsyncRequestListener {
    private AsyncNetWorkAPIFactory asyncNetWorkAPIFactory;

    // 初始化Api启动器类
    private void initFactory() {
        asyncNetWorkAPIFactory = new AsyncNetWorkAPIFactory(this);
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        onCreateBeforeSuper(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView();
        findView();
        initIntent(getIntent());
        initView();
        onCreateAfterSuper(savedInstanceState);
        initFactory();
    }

    /**
     * 获取网络启动器
     *
     * @return
     */
    protected AsyncNetWorkAPIFactory getAPIFactory() {
        if (asyncNetWorkAPIFactory == null) {
            initFactory();
        }
        return asyncNetWorkAPIFactory;
    }

    @Override
    public final void onNewIntent(Intent intent) {
        onNewIntentBeforeSuper(intent);
        super.onNewIntent(intent);
        onNewIntentAfterSuper(intent);
    }

    @Override
    public final void onRestart() {
        onRestartBeforeSuper();
        super.onRestart();
        onRestartAfterSuper();
    }

    @Override
    public final void onStart() {
        onStartBeforeSuper();
        super.onStart();
        onStartAfterSuper();
    }

    public final void onResume() {
        onResumeBeforeSuper();
        super.onResume();
        onResumeAfterSuper();
    }

    public final void onPause() {
        onPauseBeforeSuper();
        super.onPause();
        onPauseAfterSuper();
    }

    public final void onStop() {
        onStopBeforeSuper();
        super.onStop();
        onStopAfterSuper();
    }

    @Override
    public final void onDestroy() {
        onDestroyBeforeSuper();
        cancleReq();
        super.onDestroy();
        onDestroyAfterSuper();
    }

    public Activity getActivity() {
        return this;
    }

    // 取消和当前界面相关的请求
    private void cancleReq() {
        asyncNetWorkAPIFactory.cancelAllCancelableRequest();
    }

    /**
     * 由子类重写 原onCreate方法中回调此方法 回调时机super.onCreate 之前回调
     *
     * @param savedInstanceState
     */
    public void onCreateBeforeSuper(Bundle savedInstanceState) {
    }

    /**
     * 由子类重写 原onCreate方法中回调此方法 回调时机super.onCreate 之后回调
     *
     * @param savedInstanceState Bundle
     */
    public void onCreateAfterSuper(Bundle savedInstanceState) {
    }

    /**
     * 由子类重写 原onNewIntent方法中回调此方法 回调时机super.onNewIntent 之前回调
     *
     * @param savedInstanceState
     */
    public void onNewIntentBeforeSuper(Intent savedInstanceState) {
    }

    /**
     * 由子类重写 原onNewIntent方法中回调此方法 回调时机super.onNewIntent 之后回调
     *
     * @param savedInstanceState
     */
    public void onNewIntentAfterSuper(Intent savedInstanceState) {
    }

    /**
     * 由子类重写 原onStart方法中回调此方法 回调时机在super.onStart之前
     */
    public void onStartBeforeSuper() {
    }

    /**
     * 由子类重写 原onStart方法中回调此方法 回调时机在super.onStart之后
     */
    public void onStartAfterSuper() {
    }

    /**
     * 由子类重写 原onStart方法中回调此方法 回调时机在super.onReStart之前
     */
    public void onRestartBeforeSuper() {
    }

    /**
     * 由子类重写 原onStart方法中回调此方法 回调时机在super.onReStart之后
     */
    public void onRestartAfterSuper() {
    }

    /**
     * 由子类重写 原onResume方法中回调此方法 回调时机在super.onResume之前
     */
    public void onResumeBeforeSuper() {
    }

    /**
     * 由子类重写 原onResume方法中回调此方法 回调时机在super.onResume之后
     */
    public void onResumeAfterSuper() {
    }

    /**
     * 由子类重写 原onPause方法中回调此方法 回调时机在super.onPause之前
     */
    public void onPauseBeforeSuper() {
    }

    /**
     * 由子类重写 原onPause方法中回调此方法 回调时机在super.onPause之后
     */
    public void onPauseAfterSuper() {
    }

    /**
     * 由子类重写 原onStop方法中回调此方法 回调时机在super.onStop之前
     */
    public void onStopBeforeSuper() {
    }

    /**
     * 由子类重写 原onStop方法中回调此方法 回调时机在super.onStop之后
     */
    public void onStopAfterSuper() {
    }

    /**
     * 由子类重写 原onDestroy方法中回调此方法 回调时机在super.onDestroy之前
     */
    public void onDestroyBeforeSuper() {
    }

    /**
     * 由子类重写 原onDestroy方法中回调此方法 回调时机在super.onDestroy之后
     */
    public void onDestroyAfterSuper() {
    }

    @Override
    public void onResult(RequestId requestID, RespParamBase baseResponse) {

    }

    @Override
    public void onError(RequestId requestID, String errorCode,
            String errorDesc) {

    }
    /**
     * 由子类重写设置id
     */
    protected void setContentView(){

    }
    /**
     * 由子类重写 初始化控件
     */
    protected void findView() {

    }

    /**
     * 由子类重写 初始化Intent
     *
     * @param intent
     */
    protected void initIntent(Intent intent) {
    }

    /**
     * 由子类重写 初始化控件数据
     */
    protected void initView() {

    }

}
