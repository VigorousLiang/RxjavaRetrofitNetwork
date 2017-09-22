package com.vigorous.asynchronized.network.request;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Vigorous.Liang on 2017/9/22.
 */

public class DecryptResponsebodyConverter<T>
        implements Converter<ResponseBody, T> {
    private final Gson mGson;// gson对象
    private final TypeAdapter<T> adapter;

    /**
     * 构造器
     */
    public DecryptResponsebodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.mGson = gson;
        this.adapter = adapter;
    }

    /**
     * 转换
     *
     * @param responseBody
     * @return
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        String response = responseBody.string();
        // TODO Decrypt

        return adapter.fromJson(response);

    }
}
