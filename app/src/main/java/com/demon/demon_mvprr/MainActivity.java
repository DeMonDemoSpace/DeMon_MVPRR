package com.demon.demon_mvprr;

import android.widget.TextView;

import com.demon.demon_mvprr.mvpc.contract.MainContract;
import com.demon.demon_mvprr.mvpc.presenter.MainPresenter;
import com.demon.mvprr.activity.BaseActivity;
import com.demon.mvprr.model.CreatePresenter;

import butterknife.BindView;

@CreatePresenter({MainPresenter.class})
public class MainActivity extends BaseActivity implements MainContract.View {

    @BindView(R.id.text)
    TextView text;
    private MainPresenter presenter;

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initCreate() {
        presenter = getPresenter(MainPresenter.class);
        presenter.Taobao("手机");
    }

    @Override
    public void result(String s) {
        text.setText(s);
    }

}
