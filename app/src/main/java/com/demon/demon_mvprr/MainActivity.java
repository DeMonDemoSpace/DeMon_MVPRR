package com.demon.demon_mvprr;

import android.widget.TextView;
import com.demon.demon_mvprr.mvpc.contract.MainContract;
import com.demon.demon_mvprr.mvpc.presenter.MainPresenter;
import com.demon.mvprr.activity.BaseActivity;
import com.demon.mvprr.model.CreatePresenter;

@CreatePresenter({MainPresenter.class})
public class MainActivity extends BaseActivity implements MainContract.View {

    TextView text;
    private MainPresenter presenter;

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initCreate() {
        text = findViewById(R.id.text);
        presenter = getPresenter(MainPresenter.class);
        presenter.Taobao("手机");
    }

    @Override
    public void result(String s) {
        text.setText(s);
    }
}
