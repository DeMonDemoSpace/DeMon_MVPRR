package com.demon.demon_mvprr.mvpc.model;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author DeMon
 * @date 2019/8/16
 * @email 757454343@qq.com
 * @description
 */
public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse = chain.proceed(request);
        return originalResponse.newBuilder().header("assess-token", "123456").build();
    }

}

