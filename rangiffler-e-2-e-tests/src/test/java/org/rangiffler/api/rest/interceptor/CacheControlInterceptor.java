package org.rangiffler.api.rest.interceptor;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

public class CacheControlInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        okhttp3.Request request = chain.request();
        okhttp3.Request.Builder requestBuilder = request.newBuilder()
                .cacheControl(CacheControl.FORCE_NETWORK);
        return chain.proceed(requestBuilder.build());
    }
}
