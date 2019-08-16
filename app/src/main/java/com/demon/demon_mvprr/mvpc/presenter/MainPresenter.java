package com.demon.demon_mvprr.mvpc.presenter;

import com.demon.demon_mvprr.bean.TaobaoBean;
import com.demon.demon_mvprr.mvpc.contract.MainContract;
import com.demon.demon_mvprr.mvpc.model.Model;
import com.demon.demon_mvprr.mvpc.model.OnRequestListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DeMon
 * @date 2019/1/10
 * @email 757454343@qq.com
 * @description
 */
public class MainPresenter extends MainContract.Presenter {
    @Override
    public void Taobao(String q) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "utf-8");
        map.put("q", q);
        //map.put("callback", "cb");
        Model.getInstance().getTaobao(q, new OnRequestListener<TaobaoBean>() {
            @Override
            public void onSucceed(TaobaoBean bean) {
                //getView().result(s);
            }

            @Override
            public void onError(String s) {

            }

        });
        /*Model.getInstance().post("sug?", map, new OnRequestListener<String>() {
            @Override
            public void onSucceed(String s) {
                getView().result(s);
            }

            @Override
            public void onError(String s) {

            }

        });*/

       /* Model.getInstance().get("sug?code=utf-8&q=手机", new OnRequestListener<String>() {
            @Override
            public void onSucceed(String s) {
                Log.i(TAG, "onSucceed: " + s);
                getView().result(s);
            }

            @Override
            public void onError(String s) {

            }

        });*/
    }
}
