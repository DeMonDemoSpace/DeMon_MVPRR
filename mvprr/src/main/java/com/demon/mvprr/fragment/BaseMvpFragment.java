package com.demon.mvprr.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.demon.mvprr.model.BasePresenterInfc;
import com.demon.mvprr.model.BaseView;
import com.demon.mvprr.util.TUtil;

/**
 * @author DeMon
 * @date 2017/12/18
 * @description Fragment 基类
 */
public abstract class BaseMvpFragment<T extends BasePresenterInfc> extends Fragment implements BaseView {

    protected final String TAG = this.getClass().getSimpleName();
    /**
     * 使用List来保存Presenter
     * 解决一个界面多个Presenter的情况
     */
    public Context mContext;
    private boolean isFristLoad;
    private boolean isVisible;
    private boolean isResume;
    protected View containerView;
    protected T mPresenter;
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
        mPresenter = TUtil.getT(this);
        if (mPresenter!=null){
            mPresenter.setContext(mContext);
            mPresenter.onStart(this);
        }
    }

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 解绑View
     */
    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter!=null){
            mPresenter.onDestroy();
        }
    }


}