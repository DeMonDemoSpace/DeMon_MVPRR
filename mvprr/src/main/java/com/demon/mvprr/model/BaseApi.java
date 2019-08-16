package com.demon.mvprr.model;


import com.demon.mvprr.BaseApp;
import com.demon.mvprr.BuildConfig;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by DeMon on 2017/9/18.
 */

public class BaseApi {
    //超时时长，单位：毫秒
    private int TimeOut = 7676;

    public void setTimeOut(int timeOut) {
        TimeOut = timeOut;
    }

    /**
     * 使用OkHttp配置了超时及缓存策略的Retrofit
     *
     * @param baseUrl
     * @param interceptors 自定义的拦截器
     * @return
     */
    public Retrofit getRetrofit(String baseUrl, Interceptor... interceptors) {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //缓存
        File cacheFile = new File(BaseApp.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.readTimeout(TimeOut, TimeUnit.MILLISECONDS)
                .writeTimeout(TimeOut, TimeUnit.MILLISECONDS)
                .connectTimeout(TimeOut, TimeUnit.MILLISECONDS)
                .addInterceptor(new CacheControlInterceptor())
                .addNetworkInterceptor(new CacheControlInterceptor())
                .cache(cache);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(logInterceptor);
        }

        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }

        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addConverterFactory(ScalarsConverterFactory.create())//请求结果转换为基本类型，一般为String
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//适配RxJava2.0
                .build();
        return retrofit;
    }


}
