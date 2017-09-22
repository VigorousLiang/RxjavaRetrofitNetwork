package com.vigorous.asynchronized.network.data.request;

import com.google.gson.annotations.SerializedName;
import com.vigorous.asynchronized.network.data.NetWorkRequestConst;
import com.vigorous.asynchronized.network.data.RequestParamBase;

/**
 * Created by Vigorous.Liang on 2017/9/17.
 */

public class ExampleRequestParam extends RequestParamBase {

    @SerializedName(NetWorkRequestConst.SERIALIZED_REQUEST_EXAMPLE_ONCE_NO)
    private boolean all;

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

}
