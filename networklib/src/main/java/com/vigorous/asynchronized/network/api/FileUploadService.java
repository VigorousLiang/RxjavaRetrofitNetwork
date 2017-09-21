package com.vigorous.asynchronized.network.api;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by Vigorous.Liang on 2017/9/21.
 */

public interface FileUploadService {
//    @Multipart
//    @POST("AppFiftyToneGraph/videoLink")
//    Observable<ResponseBody> upload(
//            @Part("description") RequestBody description,
//            @Part MultipartBody.Part file);

    //支持动态指定上传地址
    @Multipart
    @POST()
    Observable<ResponseBody> upload(@Url String url,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file);
}
