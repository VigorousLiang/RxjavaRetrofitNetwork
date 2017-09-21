package com.vigorous.asynchronized.network.data.download;

import com.vigorous.asynchronized.network.listener.AsyncDownloadProgressListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by Vigorous.Liang on 2017/9/19.
 */

public class DownloadResponseBody extends ResponseBody {
    // 实际的待包装响应体
    private final ResponseBody responseBody;
    // 进度回调接口
    private final AsyncDownloadProgressListener progressListener;
    // 包装完成的BufferedSource
    private BufferedSource bufferedSource;

    /**
     * 构造函数，赋值
     * 
     * @param responseBody 待包装的响应体
     * @param progressListener 回调接口
     */
    public DownloadResponseBody(ResponseBody responseBody,
            AsyncDownloadProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }

    /**
     * 重写调用实际的响应体的contentType
     * 
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    /**
     * 重写调用实际的响应体的contentLength
     * 
     * @return contentLength
     * @throws IOException 异常
     */
    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    /**
     * 重写进行包装source
     * 
     * @return BufferedSource
     * @throws IOException 异常
     */
    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            // 包装
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    /**
     * 读取，回调进度接口
     * 
     * @param source Source
     * @return Source
     */
    private Source source(Source source) {

        return new ForwardingSource(source) {
            // 当前读取字节数
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                // 增加当前读取的字节数，如果读取完成了bytesRead会返回-1
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                // 回调，如果contentLength()不知道长度，会返回-1
                progressListener.update(
                        responseBody.contentLength() != 0
                                ? ((totalBytesRead * 100)
                                        / responseBody.contentLength())
                                : 100,
                        bytesRead == -1);
//                Log.e("download", "totalBytesRead" + totalBytesRead
//                        + "totalLength" + responseBody.contentLength());
                return bytesRead;
            }
        };
    }
}
