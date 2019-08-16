package com.demon.demon_mvprr.mvpc.contract;

import com.demon.demon_mvprr.bean.TaobaoBean;
import com.demon.mvprr.model.BasePresenter;
import com.demon.mvprr.model.BaseView;

/**
 * @author DeMon
 * @date 2019/1/10
 * @email 757454343@qq.com
 * @description
 */
public interface JavaBeanContract {
    interface View extends BaseView {
        void result(TaobaoBean bean);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void Taobao(String q);
    }
}
