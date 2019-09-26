package com.demo.okhttp.retrofit;

import android.content.Context;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by Administrator on 2018/8/20 0020.
 */

public class SSLSocketFactoryUtils {


    /**
     * 添加证书
     *
     */
    public static SSLSocketFactory getSocketFactory(Context context) {
        try {

            InputStream inputStream = context.getAssets().open("test_whgxwl.csr");

            SSLContext sslContext = SSLContext.getInstance("TLS");
            //使用默认证书
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            //去掉系统默认证书
            keystore.load(null);

            Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(inputStream);

            keystore.setCertificateEntry("skxy", certificate);

            if (inputStream!=null){
                inputStream.close();
            }

            //通过信任管理器获取一个默认的算法
            String algorithm = TrustManagerFactory.getDefaultAlgorithm();
            //算法工厂创建
            TrustManagerFactory instance = TrustManagerFactory.getInstance(algorithm);
            instance.init(keystore);
            sslContext.init(null, instance.getTrustManagers(), null);
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            HttpsURLConnection.setDefaultSSLSocketFactory(socketFactory);

            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
