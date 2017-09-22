package com.vigorous.asynchronized.network.request;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by Vigorous.Liang on 2017/9/22.
 */

public class EncryptedRequestbodyConverter<T>
        implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType
            .parse("application/json; charset=UTF-8");
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    /**
     * 构造器
     */

    public EncryptedRequestbodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {

        // TODO Encrypt the value str

        return RequestBody.create(MEDIA_TYPE, value.toString());
    }
}
