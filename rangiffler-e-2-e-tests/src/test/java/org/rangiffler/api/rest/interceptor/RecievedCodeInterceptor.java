package org.rangiffler.api.rest.interceptor;

import okhttp3.Interceptor;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.rangiffler.api.context.SessionStorageContext;

import java.io.IOException;

public class RecievedCodeInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        String location = response.header("Location");
        if(location != null && location.contains("code=")){
            String codeValue = StringUtils.substringAfter(location, "code=");
            SessionStorageContext.getInstance().setCode(codeValue);
        }
        return response;
    }
}
