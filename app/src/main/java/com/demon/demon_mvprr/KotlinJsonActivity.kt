package com.demon.demon_mvprr

import com.demon.demon_mvprr.mvpc.contract.KotlinJsonContract
import com.demon.demon_mvprr.mvpc.presenter.JavaJsonPresenter
import com.demon.demon_mvprr.mvpc.presenter.KotlinJsonPresenter
import com.demon.mvprr.activity.BaseActivity
import com.demon.mvprr.model.BasePresenter
import com.demon.mvprr.model.CreatePresenter

@CreatePresenter({KotlinJsonPresenter::class})
class KotlinJsonActivity : BaseActivity(), KotlinJsonContract.View {
    override fun result(s: String) {

    }


    override fun bindLayout(): Int {
        return R.layout.activity_json
    }

    override fun initCreate() {

    }


}
