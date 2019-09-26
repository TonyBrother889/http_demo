package com.demo.okhttp.retrofit;

import android.content.Context;

import com.demo.okhttp.httpinterface.HttpService;
import com.demo.okhttp.httpinterface.ServerUrl;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * 介绍：RetrofitManager  管理器
 * 时间: 2017/11/6
 *
 * @author Administrator
 */
public class RetrofitManager {

    private Retrofit retrofit;
    private HttpService httpService;

    private static class SingletonHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {

        return SingletonHolder.INSTANCE;
    }

    private RetrofitManager() {
        OkHttpClient okHttpClient = OkHttpUtils.getInstance().getOkHttpClient();
        retrofit = new Retrofit.Builder()
                /**
                 * 设置服务器地址*/
                .baseUrl(ServerUrl.setServerUrl())
                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        httpService = retrofit.create(HttpService.class);
    }

    /**
     * 设置公共参数
     */
    public static Interceptor addQueryParameterInterceptor() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        // Provide your custom parameter here
                        .addQueryParameter("utoken", "QUAifalKC9eKN_aJs-Mz_nkj7BS0tejDalU-LkLTcmtkomiEI4xAspPIn_VqErii-2mFvngFv7bV9F0YOqEbrzCbyrGMGfcfWrO4cKqZaH4i_gNI9n4d7GveVe5lDMyWzkWL-jV_bNQXH46VL93z1lYGXlpvBlG5")
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
    }

    /**
     * 设置头
     */
/*    public static Interceptor addHeaderInterceptor(final Context context) {
        *//***随机生成token***//*
        final String token = TokenUtils.getInstance().generateToken();

        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        // Provide your custom header here
                        .addHeader("AppMD5", CommonUtils.getSignValidInfo(context))
                        .addHeader("RandomToken",TokenUtils.getInstance().generateToken())

                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

        *//***发送请求时将全局变量设置为false*//*
        Constant.IS_SERVER_RETURNED_VALUE = false;

        return headerInterceptor;
    }*/


    public HttpService getDataMethord() {
        return getInstance().httpService;
    }

    public Observable getObservable(Observable<ResponseBody> observable, Type type) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new ApiResultFunc(type));
    }

    public Observable getObservable(Observable<ResponseBody> observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new ApiResultFunc());
    }
}
