package org.rangiffler.api.interceptor;


import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.rangiffler.api.context.CookieContext;
import org.rangiffler.config.ConfigHub;

import java.io.IOException;

public class AddCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();


        final CookieContext cookieContext = CookieContext.getInstance();
        String cookieXsrf = cookieContext.getCookie("XSRF-TOKEN");
        String jsessionId = cookieContext.getCookie("JSESSIONID");

        String url = originalRequest.url().url().toString();
        String method = originalRequest.method();

        if (method.equals("POST") && url.contains(ConfigHub.configEnv.authUrl() + "/login")) {
            System.out.println("СТАРОЕ!!============================= " + url);
            System.out.println("++++++++ XSRF-TOKEN = " + cookieXsrf);
            System.out.println("++++++++ JSESSIONID = " + jsessionId);
        }

        if (method.equals("GET") && url.contains("&continue")) {
            System.out.println("НОВОЕ!!============================="+ url);

            System.out.println("++++++++ XSRF-TOKEN = " + cookieXsrf);
            System.out.println("++++++++ JSESSIONID = " + jsessionId);
        }



        final Headers.Builder builder = originalRequest.headers().newBuilder();
        builder.removeAll("Cookie");
        if (jsessionId != null) {
            builder.add("Cookie", "JSESSIONID=" + jsessionId);
        }
        if (cookieXsrf != null) {
            builder.add("Cookie", "XSRF-TOKEN=" + cookieXsrf);
        }

        final Headers headers = builder.build();

        Request request = originalRequest.newBuilder()
                .method(originalRequest.method(), originalRequest.body())
                .headers(headers)
                .url(originalRequest.url())
                .build();

        return chain.proceed(request);
    }
}
