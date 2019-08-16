package com.demon.demon_mvprr.mvpc.model;


import com.demon.demon_mvprr.bean.TaobaoBean;
import com.demon.mvprr.model.BaseModel;
import com.demon.mvprr.model.BaseService;
import io.reactivex.Observer;

import java.util.Map;


public class Model extends BaseModel {
    private static final String TAG = "Model";
    private static Model instance = new Model();


    private BaseService baseService = Api.getRetrofit().create(BaseService.class);

    private ApiService apiService = Api.getRetrofit().create(ApiService.class);

    public static Model getInstance() {
        return instance;
    }

    /*public void post(String url, OnRequestListener listener) {
        Observer<String> observer = new IObserver(mContext, listener);
        addSubcription(Api.getBaseService().post(url), observer);
    }*/

    public void get(String url, OnRequestListener<String> listener) {
        Observer<String> observer = new IObserver<>(mContext, listener);
        addSubcription(baseService.get(url), observer);
    }

    public void getTaobao(Map<String, Object> maps, OnRequestListener<TaobaoBean> listener) {
        Observer<TaobaoBean> observer = new IObserver<>(mContext, listener);
        addSubcription(apiService.getTaobao(maps), observer);
    }

    public void getTaobao(String q, OnRequestListener<TaobaoBean> listener) {
        Observer<TaobaoBean> observer = new IObserver<>(mContext, listener);
        addSubcription(apiService.getTaobao("utf-8", q), observer);
    }

    public void post(String url, Map<String, Object> maps, OnRequestListener<String> listener) {
        Observer<String> observer = new IObserver<>(mContext, listener);
        addSubcription(baseService.post(url, maps), observer);
    }



    /*public void postNoDialog(String url, Map<String, Object> maps, OnRequestListener listener) {
        Observer<String> observer = new IObserver(mContext, listener, false);
        addSubcription(Api.getBaseService().post(url, maps), observer);
    }


    public void uploadFile(String url, File file, String name, OnRequestListener listener) {
        Observer<String> observer = new IObserver(mContext, listener);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", name, requestFile);
        addSubcription(Api.getBaseService().uploadsFile(url, filePart), observer);

    }*/
}
