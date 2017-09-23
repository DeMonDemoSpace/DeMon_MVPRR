package com.mvprr.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;


/**
 * Created by DeMon on 2017/9/22.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    public T mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initPresenter();
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        init();
    }

    public abstract void initPresenter();

    public abstract int getLayoutId();

    public abstract void init();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
