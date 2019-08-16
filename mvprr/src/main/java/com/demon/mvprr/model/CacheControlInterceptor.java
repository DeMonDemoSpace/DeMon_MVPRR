package com.demon.mvprr.model;

import android.text.TextUtils;
import com.demon.mvprr.BaseApp;
import com.demon.mvprr.util.NetWorkUtil;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author DeMon
 * @date 2019/8/16
 * @email 757454343@qq.com
 * @description 云端响应头拦截器，用来配置缓存策略
 */
public class CacheControlInterceptor implements Interceptor {
    /**
     * 设缓存有效期为7天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 7;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String cacheControl = request.cacheControl().toString();
        if (!NetWorkUtil.isNetConnected(BaseApp.getContext())) {
            request = request.newBuilder()
                    .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (NetWorkUtil.isNetConnected(BaseApp.getContext())) {
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                    .removeHeader("Pragma")
                    .build();
        }
    }
}
