package com.demon.demon_mvprr.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.demon.demon_mvprr.R;
import com.demon.demon_mvprr.bean.TaobaoBean;
import com.demon.demon_mvprr.mvpc.contract.JavaBeanContract;
import com.demon.demon_mvprr.mvpc.presenter.JavaBeanPresenter;
import com.demon.mvprr.activity.BaseActivity;

import java.util.List;

/**
 *
 */
public class JavaBeanActivity extends BaseActivity<JavaBeanPresenter> implements JavaBeanContract.View {

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

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    Toast.makeText(mContext, "输入内容不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                mPresenter.Taobao(s);
            }
        });

    }


    @Override
    public void result(TaobaoBean bean) {
        tv.setText("");
        for (int i = 0; i < bean.getResult().size(); i++) {
            List<String> list = bean.getResult().get(i);
            for (int j = 0; j < list.size(); j++) {
                tv.append(list.get(j) + "\n");
            }
        }
    }
}
