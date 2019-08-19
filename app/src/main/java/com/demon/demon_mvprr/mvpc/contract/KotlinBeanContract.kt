package com.demon.demon_mvprr.mvpc.contract

import com.demon.demon_mvprr.bean.TaobaoBean
import com.demon.mvprr.model.BasePresenter
import com.demon.mvprr.model.BaseView

/**
 * @author DeMon
 * @date 2019/8/16
 * @email 757454343@qq.com
 * @description
 */
class KotlinBeanContract {

    interface View : BaseView {
        fun result(bean: TaobaoBean)
    }


    abstract class Presenter : BasePresenter<View>() {
        abstract fun Taobao(s: String)
    }

}