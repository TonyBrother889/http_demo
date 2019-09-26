package com.demo.okhttp.application;

import android.app.Application;

import com.demo.okhttp.retrofit.RetrofitManager;
import com.demo.okhttp.retrofit.Tls12SocketFactory;
import com.demo.okhttp.retrofit.UnSafeTrustManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.CookieStore;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;

/**
 * Created by zhangxiang on 2016/9/19.
 */
public class MyApplication extends Application {


    private CookieJarImpl cookieJar;

    @Override
    public void onCreate() {
        super.onCreate();
        initOkHttp();
    }


    /**
     * ==============================================================初始化okhttp================================================================
     */
    private void initOkHttp() {
        cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(cookieJar);
        builder.connectTimeout(50000L, TimeUnit.MILLISECONDS);
        builder.readTimeout(50000L, TimeUnit.MILLISECONDS);
        builder.addInterceptor(new LoggerInterceptor("TAG"));
      //  builder.addInterceptor(RetrofitManager.addHeaderInterceptor(this));  //设置请求头
        builder.retryOnConnectionFailure(true);

        SSLContext tls = null;
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            tls = SSLContext.getInstance("TLS");
            tls.init(null, trustAllCerts, new SecureRandom());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        SSLSocketFactory socketFactory = new Tls12SocketFactory(tls.getSocketFactory());
        builder.sslSocketFactory(socketFactory, new UnSafeTrustManager());
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

       // SSLSocketFactory socketFactory = new Tls12SocketFactory(SSLSocketFactoryUtils.getSocketFactory(this));

        //builder.socketFactory(socketFactory);

        OkHttpClient okHttpClient = builder.build();
        OkHttpUtils.initClient(okHttpClient);
    }

}
