package com.demon.mvprr.model;


import android.content.Context;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by D&LL on 2017/5/22.
 * 封装线程管理和订阅的过程
 */
public class BaseModel {
    protected final String TAG = this.getClass().getSimpleName();
    public static Context mContext;

    protected <T> void addSubcription(Observable<T> ob, Observer<T> callback) {
        if (callback != null && ob != null) {
            ob.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(callback);
        }
    }
}

