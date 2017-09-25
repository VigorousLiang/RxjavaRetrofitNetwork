package com.vigorous.asynchronized.network.request;

import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Deal with delayed retry.(Currently not used)
 * Created by Vigorous.Liang on 2017/9/25.
 */

public class RetryWithDelay
        implements Function<Observable<? extends Throwable>, Observable<?>> {

    private final int maxRetries;
    private final int retryDelayMillis;
    private final int retryDelayMillisPerRetry;
    private static int retryCount = 0;

    public RetryWithDelay(int maxRetries, int retryDelayMillis,
            int retryDelayMillisPerRetry) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
        this.retryDelayMillisPerRetry = retryDelayMillisPerRetry;
    }

    @Override
    public Observable<?> apply(
            @NonNull Observable<? extends Throwable> observable)
            throws Exception {
        return observable.flatMap(new Function<Throwable, Observable<?>>() {
            @Override
            public Observable<?> apply(@NonNull Throwable throwable)
                    throws Exception {
                if (++retryCount <= maxRetries) {
                    // When this Observable calls onNext, the original
                    // Observable will be retried (i.e. re-subscribed).
                    return Observable.timer(retryDelayMillis
                            + retryDelayMillisPerRetry * (retryCount - 1),
                            TimeUnit.MILLISECONDS);
                }
                // Max retries hit. Just pass the error along.
                return Observable.error(throwable);
            }

        });
    }
}
