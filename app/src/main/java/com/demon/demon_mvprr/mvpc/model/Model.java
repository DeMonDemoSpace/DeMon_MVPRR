package com.demon.demon_mvprr.mvpc.model;


import com.demon.mvprr.model.BaseModel;

import java.io.File;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class Model extends BaseModel {
    private static final String TAG = "Model";
    private static Model instance = new Model();

    public static Model getInstance() {
        return instance;
    }

    public void post(String url, OnNext listener) {
        final Observer<String> observer = new IObserver(mContext, listener);
        Api.getBaseService().post(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void post(String url, Map<String, Object> maps, OnNext listener) {
        final Observer<String> observer = new IObserver(mContext, listener);
        Api.getBaseService().post(url, maps)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void postNoDialog(String url, Map<String, Object> maps, OnNext listener) {
        final Observer<String> observer = new IObserver(mContext, listener);
        Api.getBaseService().post(url, maps)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    public void uploadFile(String url, File file, String name, OnNext listener) {
        final Observer<String> observer = new IObserver(mContext, listener);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", name, requestFile);
        Api.getBaseService().uploadsFile(url, filePart)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
