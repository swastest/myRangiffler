package org.rangiffler.api;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

public abstract class BaseRestClient {
    protected final String baseServiceUrl;
    protected final OkHttpClient httpClient;
    protected final Retrofit retrofit;

    public BaseRestClient(String baseServiceUrl) {
        this(baseServiceUrl, false, null);
    }

    public BaseRestClient(String baseServiceUrl, boolean followRedirect) {
        this(baseServiceUrl, followRedirect, null);
    }

    public BaseRestClient(String baseServiceUrl, boolean followRedirect, Interceptor... interceptors) {
        this.baseServiceUrl = baseServiceUrl;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .followRedirects(followRedirect);

        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addNetworkInterceptor(interceptor);
            }
        }

        builder.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(BODY));
        this.httpClient = builder.build();

        this.retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(baseServiceUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
