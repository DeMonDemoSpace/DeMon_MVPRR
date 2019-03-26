package com.demon.demon_mvprr.mvpc.presenter;

import com.demon.demon_mvprr.mvpc.contract.MainContract;
import com.demon.demon_mvprr.mvpc.model.Model;
import com.demon.demon_mvprr.mvpc.model.OnNext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DeMon
 * @date 2019/1/10
 * @email 757454343@qq.com
 * @description
 */
public class MainPresenter extends MainContract.Presneter {
    @Override
    public void Taobao(String q) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "utf-8");
        map.put("q", q);
        map.put("callback", "cb");
        Model.getInstance().post("sug", map,new OnNext(){
            @Override
            public void onNext(String result) {
                getView().result(result);
            }
        });
    }
}
