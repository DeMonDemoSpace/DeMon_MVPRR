package com.demon.mvprr.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demon.mvprr.model.BaseModel;
import com.demon.mvprr.model.BasePresenter;
import com.demon.mvprr.model.BaseView;
import com.demon.mvprr.model.PresenterFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * @author DeMon
 * @date 2017/12/18
 * @description Activity 基类
 * 1. 封装Presenter
 * 1.1 在需要使用Presenter的Activity类上方添加注解@CreatePresenter({XXXPresenter.class})，声明所需要的Presenter
 * 1.2 实现业务逻辑的时候通过getPresenter(Class)来获取Presenter
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    protected final String TAG = this.getClass().getSimpleName();
    /**
     * 使用List来保存Presenter
     * 解决一个界面需要多个Presenter的情况
     */
    private List<BasePresenter> mPresenterList;
    public Context mContext;

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
     * 例如需要实现含有标题栏的BaseActivity可重写此方法
     */
    protected void initLayout() {
        setContentView(bindLayout());
    }

    /**
     * 获取绑定的布局
     */
    protected abstract int bindLayout();

    /**
     * 初始化Presenter
     */
    private void initPresenter() {
        createPresenter();
        attachView();
    }

    /**
     * 创建Presenter
     */
    private void createPresenter() {
        mPresenterList = PresenterFactory.getPresenter(this);
        if (mPresenterList == null) {
            mPresenterList = new ArrayList<>();
        }
    }

    /**
     * Presenter绑定V层
     */
    private void attachView() {
        // 绑定View
        for (int i = 0; i < mPresenterList.size(); i++) {
            mPresenterList.get(i).attachView(this);
        }
    }

    /**
     * 获取对应的Presenter
     *
     * @param clazz 对应的Presenter类
     * @param <T>
     * @return 对应的Presenter
     */
    @SuppressWarnings("unchecked")
    public <T extends BasePresenter> T getPresenter(Class<? extends BasePresenter> clazz) {
        for (int i = 0; i < mPresenterList.size(); i++) {
            if (mPresenterList.get(i).getClass() == clazz) {
                return (T) mPresenterList.get(i);
            }
        }
        return null;
    }

    protected void initParam(Intent intent) {

    }

    /**
     * 初始化数据
     */
    protected abstract void initCreate();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachView();
    }

    /**
     * 解绑View
     */
    private void detachView() {
        for (BasePresenter e : mPresenterList) {
            e.detachView();
        }
        mPresenterList.clear();
    }

}
