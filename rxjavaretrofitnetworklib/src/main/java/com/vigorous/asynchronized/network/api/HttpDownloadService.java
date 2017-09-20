package com.vigorous.asynchronized.network.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Vigorous.Liang on 2017/9/19.
 */

public interface HttpDownloadService {
    /**
     * download example
     * @param start
     * @param url
     * @return
     */
    /*大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String start, @Url String url);
}
