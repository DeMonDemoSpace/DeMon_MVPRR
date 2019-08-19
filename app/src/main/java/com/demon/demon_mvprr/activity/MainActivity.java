package com.demon.demon_mvprr.activity;

import android.content.Intent;
import android.view.View;
import com.demon.demon_mvprr.R;
import com.demon.mvprr.activity.BaseMvpActivity;

public class MainActivity extends BaseMvpActivity {


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
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, KotlinJsonActivity.class));
            }
        });
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, KotlinBeanActivity.class));
            }
        });

        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, KotlinFragmentActivity.class));
            }
        });
    }


}
