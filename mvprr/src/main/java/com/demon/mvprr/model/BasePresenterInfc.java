package com.demon.mvprr.model;

import android.content.Context;

/**
 * Presenter层接口，统一Presenter父类
 * 因为BasePresenter通过泛型与View接口绑定，在kotlin中调用会有泛型型变的转换问题
 * 所以该接口主要是为了解决kotlin调用的泛型型变问题
 */
public interface BasePresenterInfc {

    void setContext(Context context);

    void onStart(BaseView view);

    void onDestroy();

}
