package com.vigorous.asynchronized.network.api;

import com.vigorous.asynchronized.network.data.request.ExampleRequestParam;
import com.vigorous.asynchronized.network.data.response.ExampleEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * service统一接口数据
 */
public interface HttpRequestService {
    /**
     * post example
     * @param once_no
     * @return
     */
    @POST("AppFiftyToneGraph/videoLink")
    Observable<ExampleEntity> getAllVedio(@Body ExampleRequestParam once_no);

}
