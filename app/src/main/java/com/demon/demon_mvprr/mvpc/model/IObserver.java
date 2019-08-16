package com.demon.demon_mvprr.mvpc.model;

import android.app.ProgressDialog;
import android.content.Context;
import com.demon.mvprr.model.BaseObserver;

public class IObserver<T> extends BaseObserver<T> {
    private OnRequestListener<T> listener;

    public IObserver(Context context, OnRequestListener<T> listener) {
        super(context);
        this.listener = listener;
    }

    public IObserver(Context context, OnRequestListener<T> listener, boolean isShow) {
        super(context, isShow);
        this.listener = listener;
    }


    @Override
    protected void onNextResult(T t) {
        listener.onSucceed(t);
    }

    @Override
    protected void onErrorResult(Throwable e) {
        listener.onError(e.getMessage());
    }

    //加载进度框，重写可自定义
    @Override
    public ProgressDialog initDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("数据加载中");
        return dialog;
    }
}

