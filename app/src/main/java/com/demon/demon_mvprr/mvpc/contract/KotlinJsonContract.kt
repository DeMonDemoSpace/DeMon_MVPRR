package com.demon.demon_mvprr.mvpc.contract

import com.demon.mvprr.model.BaseView

/**
 * @author DeMon
 * @date 2019/8/16
 * @email 757454343@qq.com
 * @description
 */
class KotlinJsonContract {

    interface View : BaseView {
        fun result(s: String)
    }

}