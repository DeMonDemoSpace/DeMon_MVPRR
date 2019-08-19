package com.demon.mvprr.model;

import android.content.Context;

/**
 *
 */
public interface BasePresenterInfc {

   void setContext(Context context);

   void onStart(BaseView view);

   void onDestroy();

}
