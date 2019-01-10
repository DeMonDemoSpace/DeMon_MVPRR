package com.demon.mvprr.model;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

public interface BaseService {
    @GET()
    Observable<String> get(@Url String url); //get请求，无参数

    @FormUrlEncoded
    @GET()
    Observable<String> get(@Url String url, @FieldMap Map<String, Object> maps);//get请求，有参数

    @POST()
    Observable<String> post(@Url String url);//post请求，无参数

    @FormUrlEncoded
    @POST()
    Observable<String> post(@Url String url, @FieldMap Map<String, Object> maps);//post请求，有参数

    @Multipart
    @POST()
    Observable<String> uploadsFile(@Url String url, @Part() MultipartBody.Part part);//单文件上传


    @Multipart
    @POST()
    Observable<String> uploadFiles(@Url String url, @PartMap() Map<String, RequestBody> maps);//多文件上传

}
