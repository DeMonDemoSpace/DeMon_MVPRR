package com.demon.demon_mvprr.fragment

import android.widget.Toast
import com.demon.demon_mvprr.R
import com.demon.demon_mvprr.bean.TaobaoBean
import com.demon.demon_mvprr.mvpc.contract.KotlinBeanContract
import com.demon.demon_mvprr.mvpc.presenter.KotlinBeanPresenter
import com.demon.mvprr.fragment.BaseMvpFragment
import kotlinx.android.synthetic.main.activity_json.*

/**
 * @author DeMon
 * @date 2019/8/19
 * @email 757454343@qq.com
 * @description
 */
class KtBeanFragment : BaseMvpFragment<KotlinBeanPresenter>(), KotlinBeanContract.View {
    override fun isLazyLoad(): Boolean {
        return false
    }

    override fun bindLayout(): Int {
        return R.layout.activity_json
    }

    override fun initView() {

    }

    override fun initData() {
        btn.setOnClickListener {
            val s = etContent.text.toString()
            if (s.isNullOrEmpty()) {
                Toast.makeText(mContext, "搜索内容不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mPresenter.Taobao(s)
        }
    }

    override fun result(bean: TaobaoBean) {
        text.text = ""
        for (i in 0 until bean.result.size) {
            val list = bean.result[i]
            for (j in 0 until list.size) {
                text.append(list[j] + "\n")
            }
        }
    }
}