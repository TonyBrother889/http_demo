/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.demo.okhttp.retrofit;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;


/**
 * 介绍：数据管理类
 * 时间: 2017/11/7
 *
 * @author Administrator
 */
public class ApiResultFunc<T> implements Function<ResponseBody, BaseResultEntity<T>> {

    protected Type type;
    protected Gson gson;

    public ApiResultFunc(Type type) {
        gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .create();
        this.type = type;
    }

    public ApiResultFunc() {
        gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .create();
    }

    @Override
    public BaseResultEntity<T> apply(@NonNull ResponseBody responseBody) throws Exception {
        BaseResultEntity<T> apiResult = new BaseResultEntity<>();
        apiResult.setCode(-1);
        /***自定义ApiResult**/
        if (type != null) {
            final String response = responseBody.string();
            Log.e("json666", response + "");
            apiResult = parseApiResult(response, apiResult);
            if (apiResult.getData() != null) {
                T data = gson.fromJson(apiResult.getData().toString(), type);
                apiResult.setData(data);
            } else {
                apiResult.setMsg(response);
            }
        } else {//默认Apiresult
            try {
                final String response = responseBody.string();
                Log.e("json5333", response + "");
                BaseResultEntity result = parseApiResult(response, apiResult);
                if (result != null) {
                    apiResult = result;
                } else {
                    apiResult.setMsg(response);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                apiResult.setMsg(e.getMessage());
            }finally {
                responseBody.close();
            }
        }
        return apiResult;
    }

    private BaseResultEntity parseApiResult(String response, BaseResultEntity apiResult) throws JSONException {
        if (TextUtils.isEmpty(response)) {
            return apiResult;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("Code")) {
                apiResult.setCode(jsonObject.getInt("Code"));
            }
            if (jsonObject.has("result")) {
                apiResult.setResult(jsonObject.getString("result"));
            }
            if (jsonObject.has("data")) {
                apiResult.setData(jsonObject.getString("data"));
            }
            if (jsonObject.has("msg")) {
                apiResult.setMsg(jsonObject.getString("msg"));
            }
            if (jsonObject.has("line")) {
                apiResult.setLine(jsonObject.getInt("line"));
            }
        } catch (JSONException e) {
            apiResult.setMsg(response);
            return apiResult;
        }
        return apiResult;
    }
}
