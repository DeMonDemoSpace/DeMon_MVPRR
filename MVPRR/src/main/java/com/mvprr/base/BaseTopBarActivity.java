package com.mvprr.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.mvprr.R;

import butterknife.ButterKnife;



/**
 * 封装的带TopBar的Activity
 * 减少在xml中的include
 */
public abstract class BaseTopBarActivity<T extends BasePresenter> extends AppCompatActivity {
    public T mPresenter;
    Toolbar toolbar;
    FrameLayout viewContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_top_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewContent = (FrameLayout) findViewById(R.id.viewContent);

        //初始化设置ToolBar，必须放在toolbar事件之前
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //返回键事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //将继承 TopBarBaseActivity 的布局解析到 FrameLayout里面
        LayoutInflater.from(this).inflate(getLayoutId(), viewContent);
        ButterKnife.bind(this);
        initPresenter();

        if (mPresenter != null) {
            mPresenter.mContext = this;
        }

        init();
    }

    //设置标题
    protected void setTopBarText(String text) {
        toolbar.setTitle(text);
    }

    //设置标题蓝色
    protected void setTopBarColor(int colorId) {
        toolbar.setTitleTextColor(getResources().getColor(colorId));
    }

    //设置标题栏背景颜色
    protected void setToolbarBackground(int colorId) {
        toolbar.setBackgroundColor(colorId);
    }

    //初始化Presenter
    public abstract void initPresenter();

    //获取继承的布局
    public abstract int getLayoutId();

    //初始化继承后onCreate()业务
    public abstract void init();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
