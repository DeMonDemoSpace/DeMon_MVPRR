package com.demon.demon_mvprr.mvpc.presenter;

import android.util.Log;
import com.demon.demon_mvprr.mvpc.contract.JavaJsonContract;
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
public class JavaJsonPresenter extends JavaJsonContract.Presenter {
    @Override
    public void Taobao(String q) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "utf-8");
        map.put("q", q);
        Model.getInstance().post("sug?", map, new OnRequestListener<String>() {
            @Override
            public void onSucceed(String s) {
                getView().result(s);
            }

            @Override
            public void onError(String s) {
                Log.i(TAG, "onError: " + s);
            }

        });

    }
}
