package com.demon.demon_mvprr.mvpc.presenter

import com.demon.demon_mvprr.bean.TaobaoBean
import com.demon.demon_mvprr.mvpc.contract.KotlinBeanContract
import com.demon.demon_mvprr.mvpc.model.Model
import com.demon.demon_mvprr.mvpc.model.OnRequestListener

/**
 * @author DeMon
 * @date 2019/8/19
 * @email 757454343@qq.com
 * @description
 */
class KotlinBeanPresenter : KotlinBeanContract.Presenter() {
    override fun Taobao(s: String) {
        Model.getInstance().getTaobao(s,object :OnRequestListener<TaobaoBean>{
            override fun onSucceed(t: TaobaoBean) {
                mView.result(t)
            }

            override fun onError(s: String?) {

            }
        })
    }
}