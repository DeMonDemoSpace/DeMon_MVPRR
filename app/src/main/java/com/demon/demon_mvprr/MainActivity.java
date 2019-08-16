package com.demon.demon_mvprr;

import android.content.Intent;
import android.view.View;
import com.demon.mvprr.activity.BaseActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initCreate() {
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, JavaJsonActivity.class));
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, JavaBeanActivity.class));
            }
        });
    }


}
