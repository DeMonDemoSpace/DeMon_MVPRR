package com.demon.demon_mvprr.mvpc.presenter;

import com.demon.demon_mvprr.bean.TaobaoBean;
import com.demon.demon_mvprr.mvpc.contract.JavaBeanContract;
import com.demon.demon_mvprr.mvpc.model.Model;
import com.demon.demon_mvprr.mvpc.model.OnRequestListener;

/**
 * @author DeMon
 * @date 2019/1/10
 * @email 757454343@qq.com
 * @description
 */
public class JavaBeanPresenter extends JavaBeanContract.Presenter {
    @Override
    public void Taobao(String q) {
        Model.getInstance().getTaobao(q, new OnRequestListener<TaobaoBean>() {
            @Override
            public void onSucceed(TaobaoBean bean) {
               getView().result(bean);
            }

            @Override
            public void onError(String s) {

            }
        });

    }
}
