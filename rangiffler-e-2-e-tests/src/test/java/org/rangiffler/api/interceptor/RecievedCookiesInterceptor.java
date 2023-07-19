package org.rangiffler.api.interceptor;

import okhttp3.Interceptor;
import okhttp3.Response;
import org.rangiffler.api.context.CookieContext;
import org.rangiffler.config.ConfigHub;

import java.io.IOException;
import java.util.List;

public class RecievedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String url = chain.request().url().toString();
        String method = chain.request().method().toString();

        Response response = chain.proceed(chain.request());
        List<String> setCookie = response.headers("Set-Cookie");

        if (url.equals(ConfigHub.configEnv.authUrl() + "/login") && method.equals("POST")) {
            System.out.println("========================");
            System.out.println(setCookie);
        }

        if (!setCookie.isEmpty()) {
            for (String cookie : setCookie) {
                String[] cookies = cookie.split(";");
                for (String c : cookies) {
                    if (c.contains("XSRF-TOKEN") || c.contains("JSESSIONID")) {
                        String[] res = c.split("=");
                        if (res.length == 2) {
                            CookieContext.getInstance().setCookie(res[0], res[1]);
                        } else {
                            CookieContext.getInstance().deleteCookie(res[0]);
                        }
                    }
                }
            }
        }
        System.out.println("++++++++++++++++++");
        System.out.println(CookieContext.getInstance().getFormattedCookie("XSRF-TOKEN"));
        System.out.println(CookieContext.getInstance().getFormattedCookie("JSESSIONID"));
        return response;
    }
}
