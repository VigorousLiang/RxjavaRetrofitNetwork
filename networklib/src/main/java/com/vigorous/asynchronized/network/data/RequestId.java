package com.vigorous.asynchronized.network.data;

/**
 * Created by Vigorous.Liang on 2017/9/18.
 */

public class RequestId {

    private int mID;
    private Object mAdditionalData = null;

    public RequestId(int id) {
        mID = id;
    }

    public RequestId(int requestID, Object data) {
        mID = requestID;
        mAdditionalData = data;
    }

    public int getID() {
        return mID;
    }

    public Object getData() {
        return mAdditionalData;
    }

    public void setData(Object data) {
        mAdditionalData = data;
    }

    @Override
    public String toString() {
        return "{ID:" + mID + ", data:" + mAdditionalData + "}";
    }
}
