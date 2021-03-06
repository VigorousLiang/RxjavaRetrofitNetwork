package com.vigorous.asynchronized.network.data;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Vigorous.Liang on 2017/9/15.
 */

public class RequestParamBase implements Serializable{

    private String reserve;

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    public String toString() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}
