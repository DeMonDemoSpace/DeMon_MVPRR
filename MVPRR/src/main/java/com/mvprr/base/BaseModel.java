package com.mvprr.base;

import android.content.Context;


import com.mvprr.progress.ObserverOnNextListener;
import com.mvprr.progress.ProgressObserver;

import io.reactivex.Observable;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DeMon on 2017/9/18.
 */

public class BaseModel<T> {
    /**
     * 封装线程管理和订阅的过程
     */
    public void Subscribe(Context context, final Observable observable, ObserverOnNextListener<T>
            listener) {
        final Observer<T> observer = new ProgressObserver<T>(context, listener);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}
