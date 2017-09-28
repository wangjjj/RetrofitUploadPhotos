package com.cocoon.jay.retrofituploadphotos.retrofitutils;


import com.cocoon.jay.retrofituploadphotos.MyApplication;
import com.cocoon.jay.retrofituploadphotos.R;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by Yolan on 2017/6/23.
 */

public interface ApiService {

     String Base_URL = "http://test.xxx.cn";//测试站



    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new MyApplication.LoggingInterceptor())
//                .addNetworkInterceptor(
//                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .cookieJar(new CookieManger(MyApplication.application))
            .sslSocketFactory(HttpsUtils.getCertificates(MyApplication.application,new int[]{R.raw.xxxcer}))
            .build();

    Retrofit Scalarsretrofit = new Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Base_URL)
            .addConverterFactory(ScalarsConverterFactory.create())//直接得到json数据
            .build();


    Retrofit Gsonretrofit = new Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Base_URL)
            .addConverterFactory(GsonConverterFactory.create())//直接得到JavaBean
            .build();


    //登录
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/xxx/xxx/login")
    Call<String> login(@Body RequestBody loginBody);

    //获取图片
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/xxx/xxx/getImgs")
    Call<String> getImgs(@Body RequestBody Body);

    //上传图片
    @Multipart
    @POST("/xxx/xxx/upImg")
    Call<String> upImg(@PartMap Map<String, RequestBody> params);

    //提交
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/xxx/xxx/saveagreement")
    Call<String> getSaveagreement(@Body RequestBody Body);

    //删除图片
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/xxx/xxx/saveagreement")
    Call<String> delImg(@Body RequestBody Body);



}
