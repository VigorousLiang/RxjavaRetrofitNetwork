package com.vigorous.asynchronized.network.listener;

import com.vigorous.asynchronized.network.data.RequestId;
import com.vigorous.asynchronized.network.data.RespParamBase;

/**
 * Created by Vigorous.Liang on 2017/9/18.
 */

public interface AsyncRequestListener {

    // 网络正确返回回调
    public void onResult(RequestId requestID, RespParamBase baseResponse);

    // 网络错误返回回调
    public void onError(RequestId requestID, String errorCode, String errorDesc);
}
