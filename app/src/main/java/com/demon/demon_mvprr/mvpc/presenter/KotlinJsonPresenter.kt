package com.demon.demon_mvprr.mvpc.presenter

import com.demon.demon_mvprr.mvpc.contract.KotlinJsonContract
import com.demon.demon_mvprr.mvpc.model.Model
import com.demon.demon_mvprr.mvpc.model.OnRequestListener
import java.util.*

/**
 * @author DeMon
 * @date 2019/8/16
 * @email 757454343@qq.com
 * @description
 */
class KotlinJsonPresenter : KotlinJsonContract.Presenter() {
    override fun Taobao(q: String) {
        val map = HashMap<String, Any>()
        map["code"] = "utf-8"
        map["q"] = q
        Model.getInstance().post("sug?", map, object : OnRequestListener<String> {
            override fun onSucceed(t: String) {
                view.result(t)
            }

            override fun onError(s: String) {

            }
        })
    }
}