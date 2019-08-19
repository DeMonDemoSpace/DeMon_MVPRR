package com.demon.demon_mvprr.activity

import android.widget.Toast
import com.demon.demon_mvprr.R
import com.demon.demon_mvprr.mvpc.contract.KotlinJsonContract
import com.demon.demon_mvprr.mvpc.presenter.KotlinJsonPresenter
import com.demon.mvprr.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_json.*

class KotlinJsonActivity : BaseActivity<KotlinJsonPresenter>(), KotlinJsonContract.View {

    override fun bindLayout(): Int {
        return R.layout.activity_json
    }

    override fun initCreate() {
        btn.setOnClickListener {
            val s = etContent.text.toString()
            if (s.isNullOrEmpty()) {
                Toast.makeText(mContext, "搜索内容不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mPresenter.Taobao(s)
        }
    }


    override fun result(s: String) {
        text.text = s
    }

}
