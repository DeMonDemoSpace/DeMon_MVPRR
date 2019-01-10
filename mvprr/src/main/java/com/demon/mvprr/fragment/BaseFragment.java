package com.demon.mvprr.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demon.mvprr.model.BasePresenter;
import com.demon.mvprr.model.BaseView;
import com.demon.mvprr.model.PresenterFactory;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    Unbinder unbinder;
    public Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(bindLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initPresenter();
        initCreate();
        return view;
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
     * 初始化布局和数据
     */
    protected abstract void initCreate();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
