package com.vigorous.asynchronized.network.data;

/**
 * Created by Vigorous.Liang on 2017/9/18.
 */

public class NetWorkRequestConst {

    public final static int REQUEST_EXAMPLE = 1;
    public final static int REQUEST_NEXT = REQUEST_EXAMPLE + 1;

    public final static String RESPONSE_NORMAL = "1";
    public final static String RESPONSE_TIME_OUT = "98";
    public final static String RESPONSE_SESSION_INVALID = "99";


    public final static String RESPONSE_TIME_OUT_STR = "Network error";
    public final static String DOWNLOAD_PARAM_INVALID = "Download param invalid";
    public final static String DOWNLOAD_FILE_ALREADY_EXIST = "Download file already exist";
    public final static String EXTERNAL_STORAGE_WRITE_PERMISSION_INVALID = "External storage write permission invalid";
    public final static String WIFI_UNAVAILABLE = "WIFI is unavailable";
    /*
    Base response param
     */
    public final static String SERIALIZED_RESPONSE_CODE = "ret";
    public final static String SERIALIZED_RESPONSE_MSG = "msg";

    /*
    Example request param
     */
    public final static String SERIALIZED_REQUEST_EXAMPLE_ONCE_NO = "once_no";
    /*
    Example resp param
     */
    public final static String SERIALIZED_RESPONSE_EXAMPLE_DATA = "data";
    public final static String SERIALIZED_RESPONSE_EXAMPLE_ID = "id";
    public final static String SERIALIZED_RESPONSE_EXAMPLE_NAME = "name";
    public final static String SERIALIZED_RESPONSE_EXAMPLE_URL = "url";
    public final static String SERIALIZED_RESPONSE_EXAMPLE_TITLE = "title";
}
