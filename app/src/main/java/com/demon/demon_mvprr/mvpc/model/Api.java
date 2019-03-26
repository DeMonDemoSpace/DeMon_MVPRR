package com.demon.demon_mvprr.mvpc.model;

import com.demon.mvprr.model.BaseApi;
import com.demon.mvprr.model.BaseService;

public class Api {

    //测试api，淘宝商品搜索建议
    //访问http://www.bejson.com/knownjson/webInterface/可见
    public static String base_url = "https://suggest.taobao.com/";

    public volatile static BaseService apiService;

    public static BaseService getBaseService() {
        if (apiService == null) {
            synchronized (Api.class) {
                if (apiService == null) {
                    new Api();
                }
            }
        }
        return apiService;
    }

    public Api() {
        BaseApi baseApi = new BaseApi();
        apiService = baseApi.getRetrofit(base_url).create(BaseService.class);
    }

}
