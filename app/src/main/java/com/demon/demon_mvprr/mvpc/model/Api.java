package com.demon.demon_mvprr.mvpc.model;

import com.demon.mvprr.model.BaseApi;
import retrofit2.Retrofit;

public class Api {

    //测试api，淘宝商品搜索建议
    //访问http://www.bejson.com/knownjson/webInterface/可见
    private static String base_url = "https://suggest.taobao.com/";

    private volatile static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (Api.class) {
                if (retrofit == null) {
                    new Api();
                }
            }
        }
        return retrofit;
    }

    private Api() {
        BaseApi baseApi = new BaseApi();
        retrofit = baseApi.getRetrofit(base_url, new TokenInterceptor());
    }


}
