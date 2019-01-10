package com.demon.mvprr.model;

/**
 * @author DeMon
 * @date 2017/12/18
 * @description Presenter 基类，指定绑定的view必须继承自 IBaseView
 */

public abstract class BasePresenter<V extends BaseView> {
    protected final String TAG = this.getClass().getSimpleName();
    private V mView;

    /**
     * 绑定V层
     *
     * @param view V层
     */
    public void attachView(V view) {
        mView = view;
    }

    /**
     * 解绑V层
     */
    public void detachView() {
        mView = null;
    }

    /**
     * 获取V层
     */
    public V getView() {
        return mView;
    }

}
