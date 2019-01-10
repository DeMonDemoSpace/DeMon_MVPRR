package com.demon.demon_mvprr.mvpc.model;

import com.demon.baseframe.model.BaseApi;
import com.demon.baseframe.model.BaseService;

public class Api {
    //http://192.168.1.189:8056/DocTouchService.asmx/
    //http://192.168.0.200:20473/MPService.asmx/
    public static String base_url;

    public volatile static BaseService apiService;

    public static BaseService getBaseService() {
        if (apiService == null) {
            synchronized (ApiNurTouch.class) {
                if (apiService == null) {
                    new ApiNurTouch();
                }
            }
        }
        return apiService;
    }

    public ApiNurTouch() {
        BaseApi baseApi = new BaseApi();
        apiService = baseApi.getRetrofit(base_url).create(BaseService.class);
    }

    public static void setBase_url(String ipPort) {
        apiService = null;
        base_url = "http://" + ipPort + "/MPService.asmx/";
    }
}
