package com.demon.demon_mvprr.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.demon.demon_mvprr.R;
import com.demon.demon_mvprr.mvpc.contract.JavaJsonContract;
import com.demon.demon_mvprr.mvpc.presenter.JavaJsonPresenter;
import com.demon.mvprr.fragment.BaseFragment;

/**
 * @author DeMon
 * @date 2019/8/19
 * @email 757454343@qq.com
 * @description
 */
public class JavaJsonFragment extends BaseFragment<JavaJsonPresenter> implements JavaJsonContract.View {
    private EditText et;
    private TextView tv;

    @Override
    public boolean isLazyLoad() {
        return false;
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_json;
    }

    @Override
    protected void initView() {
        et = containerView.findViewById(R.id.etContent);
        tv = containerView.findViewById(R.id.text);
        containerView.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
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
    protected void initData() {

    }

    @Override
    public void result(String s) {
        tv.setText(s);
    }
}
