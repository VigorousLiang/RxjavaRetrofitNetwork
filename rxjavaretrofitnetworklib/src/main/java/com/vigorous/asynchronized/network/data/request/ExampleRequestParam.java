package com.vigorous.asynchronized.network.data.request;

import com.google.gson.annotations.SerializedName;
import com.vigorous.asynchronized.network.data.NetWorkRequestConst;
import com.vigorous.asynchronized.network.data.RequestParamBase;

/**
 * Created by Vigorous.Liang on 2017/9/17.
 */

public class ExampleRequestParam extends RequestParamBase {

    @SerializedName(NetWorkRequestConst.SERIALIZED_REQUEST_EXAMPLE_ONCE_NO)
    private boolean once_no;

    public boolean isOnce_no() {
        return once_no;
    }

    public void setOnce_no(boolean once_no) {
        this.once_no = once_no;
    }
}
