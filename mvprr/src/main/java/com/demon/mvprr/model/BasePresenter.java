package com.demon.mvprr.model;

import android.content.Context;

/**
 * @author DeMon
 * @date 2017/12/18
 * @description Presenter 基类，指定绑定的view必须继承自 IBaseView
 */

public class BasePresenter<V extends BaseView> implements BasePresenterInfc {
    protected final String TAG = this.getClass().getSimpleName();
    protected V mView;
    protected Context mContext;


    /**
     * 获取V层
     */
    public V getView() {
        return mView;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    /**
     * 绑定V层
     *
     * @param view V层
     */
    @Override
    public void onStart(BaseView view) {
        mView = (V) view;
    }

    /**
     * 解绑V层
     */
    @Override
    public void onDestroy() {
        mView = null;
    }

}
