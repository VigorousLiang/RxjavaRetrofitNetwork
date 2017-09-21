package com.vigorous.asynchronized.network.data.response;


import com.google.gson.annotations.SerializedName;
import com.vigorous.asynchronized.network.data.NetWorkRequestConst;

/**
 * 测试显示数据
 * Created by WZG on 2016/7/16.
 */
public class Subject {
    @SerializedName(NetWorkRequestConst.SERIALIZED_RESPONSE_EXAMPLE_ID)
    private int id;
    @SerializedName(NetWorkRequestConst.SERIALIZED_RESPONSE_EXAMPLE_NAME)
    private String name;
    @SerializedName(NetWorkRequestConst.SERIALIZED_RESPONSE_EXAMPLE_TITLE)
    private String title;
    @SerializedName(NetWorkRequestConst.SERIALIZED_RESPONSE_EXAMPLE_URL)
    private String url;

    @Override
    public String toString() {
        return "名：" + name + "\n标题:" + title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}