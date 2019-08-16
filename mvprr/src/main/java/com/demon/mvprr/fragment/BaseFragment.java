package com.demon.mvprr.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demon.mvprr.model.BasePresenter;
import com.demon.mvprr.model.BaseView;
import com.demon.mvprr.model.PresenterFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment 基类
 * Created by ZCC on 2017/11/4.
 * 1. 封装Presenter
 * 1.1 在需要使用Presenter的Fragment类上方添加注解@CreatePresenter({XXXPresenter.class})，声明所需要的Presenter
 * 1.2 实现业务逻辑的时候通过getPresenter(Class)来获取Presenter
 */
public abstract class BaseFragment extends Fragment implements BaseView {

    protected final String TAG = this.getClass().getSimpleName();
    /**
     * 使用List来保存Presenter
     * 解决一个界面多个Presenter的情况
     */
    private List<BasePresenter> mPresenterList;
    public Context mContext;
    private boolean isFristLoad;
    private boolean isVisible;
    private boolean isResume;
    protected View containerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        isFristLoad = true;
        containerView = inflater.inflate(bindLayout(), container, false);
        initPresenter();
        initView();
        return containerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isLazyLoad()) {
            initData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isVisible) {
            isResume = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible && isResume) {
            isResume = false;
            initResumeData();
        }
    }

    //返回Fragment需要做一些业务，如刷新列表数据，重写此方法即可
    //此方法只会在当前返回的时候调用
    protected void initResumeData() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisibleToUser && isLazyLoad() && isFristLoad) {
            isFristLoad = false;
            initData();
        }

    }

    public abstract boolean isLazyLoad();//是否时懒加载，即可见时才加载数据

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
        Log.d(TAG, clazz.getSimpleName() + " -- 找不到类");
        return null;
    }

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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