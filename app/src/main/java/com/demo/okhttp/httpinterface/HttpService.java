package com.demo.okhttp.httpinterface;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


/**
 * service统一接口数据
 *
 * @author Administrator
 */
public interface HttpService {

    @FormUrlEncoded
    @POST(ServerUrl.LOGIN)
    Observable<ResponseBody> login(@Field("admin_name") String username, @Field("admin_pass") String password, @Field("language") String language);

    @POST(ServerUrl.SIGN_OUT)
    Observable<ResponseBody> signOut();



}








