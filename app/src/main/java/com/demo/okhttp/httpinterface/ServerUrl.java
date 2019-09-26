package com.demo.okhttp.httpinterface;


import com.demo.okhttp.BuildConfig;

/**
 * @author Created by zhangxiang on 2016/9/20.
 *         <p>
 *         接口列表
 * @info 接口文档地址: https://apizza.net/console/project/3de3232b898d784d51731efc2a90b3ba/dev
 * 注册账号 加入组织，
 * 账号1： 847874028@qq.com
 */
public class ServerUrl {
    /**
     * 如果是debug的就是测试站
     */
    public static String setServerUrl() {

        return BuildConfig.DEBUG ? TEST_SERVER_URL : SERVER_URL;
    }
    /***绑定cg专用测试站地址*/
    private static final String BIND_CG_TEST_SERVER_URL = "http://test.whgxwl.com:8008/YX_sJsBEkT12004/";

    /**
     * 测试站
     */
    private static final String TEST_SERVER_URL = "https://test.whgxwl.com:10030/YX_kVc2yo2cmw0U/";


    /***本地环境*/
    private static final String LOCAL_URL = "http://10.32.180.7/fs-manager/YX_sJsBEkT12004/";

    /**
     * 正式站/*-
     */
    private static final String SERVER_URL = "http://cn.fs.com:8006/YX_sJsBEkT12004/";


    /**
     * 登录接口
     */
    static final String LOGIN = "fs_app.php?interface=login";

    /**
     * 退出登录
     */
    static final String SIGN_OUT = "fs_app.php?interface=login&action=logoff";

}

