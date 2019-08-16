package com.demon.demon_mvprr;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.demon.demon_mvprr.mvpc.contract.JavaJsonContract;
import com.demon.demon_mvprr.mvpc.presenter.JavaJsonPresenter;
import com.demon.mvprr.activity.BaseActivity;
import com.demon.mvprr.model.CreatePresenter;

@CreatePresenter({JavaJsonPresenter.class})
public class JavaJsonActivity extends BaseActivity implements JavaJsonContract.View {

    private JavaJsonPresenter presenter;
    private EditText et;
    private TextView tv;

    @Override
    protected int bindLayout() {
        return R.layout.activity_json;
    }

    @Override
    protected void initCreate() {
        et = findViewById(R.id.etContent);
        tv = findViewById(R.id.text);
        presenter = getPresenter(JavaJsonPresenter.class);


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    Toast.makeText(mContext, "输入内容不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.Taobao(s);
            }
        });

    }

    @Override
    public void result(String s) {
        tv.setText(s);
    }
}
