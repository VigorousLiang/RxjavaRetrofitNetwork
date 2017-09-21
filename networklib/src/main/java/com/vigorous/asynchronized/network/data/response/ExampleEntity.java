package com.vigorous.asynchronized.network.data.response;

import com.google.gson.annotations.SerializedName;
import com.vigorous.asynchronized.network.data.NetWorkRequestConst;
import com.vigorous.asynchronized.network.data.RespParamBase;

import java.util.List;

/**
 * 直接请求返回数据格式
 */
public class ExampleEntity extends RespParamBase {
    @SerializedName(NetWorkRequestConst.SERIALIZED_RESPONSE_EXAMPLE_DATA)
    private List<Subject> data;

    public List<Subject> getData() {
        return data;
    }

    public void setData(List<Subject> data) {
        this.data = data;
    }
}