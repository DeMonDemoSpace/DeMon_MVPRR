package com.demon.mvprr.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.demon.mvprr.model.BaseModel;
import com.demon.mvprr.model.BasePresenterInfc;
import com.demon.mvprr.model.BaseView;
import com.demon.mvprr.util.TUtil;


/**
 * @author DeMon
 * @date 2017/12/18
 * @description Activity 基类
 */
public abstract class BaseMvpActivity<T extends BasePresenterInfc> extends AppCompatActivity implements BaseView {
    protected final String TAG = this.getClass().getSimpleName();
    public Context mContext;
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseModel.mContext = this;
        mContext = this;
        initParam(getIntent());
        initLayout();
        initPresenter();
        initCreate();
    }


    /**
     * 初始化布局
     * 封装成方法的目的：
     * 例如需要实现含有标题栏的BaseMvpActivity可重写此方法
     */
    protected void initLayout() {
        setContentView(bindLayout());
    }

    /**
     * 获取绑定的布局
     */
    protected abstract int bindLayout();

    /**
     * 获取泛型实例化Presenter
     * 绑定View
     */
    private void initPresenter() {
        mPresenter = TUtil.getT(this);
        if (mPresenter!=null){
            mPresenter.setContext(mContext);
            mPresenter.onStart(this);
        }
    }


    protected void initParam(Intent intent) {

    }


    protected abstract void initCreate();

    /**
     * 解绑View
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.onDestroy();
        }
    }


}
