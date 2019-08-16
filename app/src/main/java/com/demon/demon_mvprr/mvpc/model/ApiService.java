package com.demon.demon_mvprr.mvpc.model;

import com.demon.demon_mvprr.bean.TaobaoBean;
import io.reactivex.Observable;
import retrofit2.http.*;

import java.util.Map;


/**
 * @author DeMon
 * @date 2019/8/16
 * @email 757454343@qq.com
 * @description
 */
public interface ApiService {
    @POST("sug?")
    Observable<TaobaoBean> getTaobao(@Body Map<String, Object> map);


    @FormUrlEncoded
    @POST("sug?")
    Observable<TaobaoBean> getTaobao(@Field("code") String code, @Field("q") String q);
}
