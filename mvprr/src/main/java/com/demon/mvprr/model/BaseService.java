package com.demon.mvprr.model;


import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.*;

import java.util.Map;

/**
 * 通用的接口Service，
 * 可使用url访问，map传值，返回json字符串
 */
public interface BaseService {

    @GET()
    Observable<String> get(@Url String url);

    @POST()
    Observable<String> post(@Url String url);

    @FormUrlEncoded
    @POST()
    Observable<String> post(@Url String url, @FieldMap Map<String, Object> maps);

    @Multipart
    @POST()
    Observable<String> uploadsFile(@Url String url, @Part() MultipartBody.Part part);

    @Multipart
    @POST()
    Observable<String> uploadFiles(@Url String url, @PartMap() Map<String, RequestBody> maps);

}
