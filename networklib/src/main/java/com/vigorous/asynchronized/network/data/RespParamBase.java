package com.vigorous.asynchronized.network.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vigorous.Liang on 2017/9/15.
 */

public class RespParamBase implements Serializable {

    @SerializedName(NetWorkRequestConst.SERIALIZED_RESPONSE_CODE)
    private String respCode="";
    @SerializedName(NetWorkRequestConst.SERIALIZED_RESPONSE_MSG)
    private String desc="";


    public String getRespCode() {
        return respCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
