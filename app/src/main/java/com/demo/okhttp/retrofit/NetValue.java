package com.demo.okhttp.retrofit;

/**
 * @author Tony.Zhang
 * @description 网络常量
 * @create 2018-6-30
 */
public class NetValue {
    /**
     * 默认超时，单位：毫秒
     **/
    public static final int DEFAULT_TIMEOUT = 30 * 1000;

    /**
     * 没有连接网络
     */
    public static final int STATUS_NO_NETWORK = 1000;
    /**
     * 连接超时
     */
    public static final int STATUS_TIMEOUT = 1001;
    /**
     * 未知错误
     */
    public static final int STATUS_UNKNOWN = 1002;
    /**
     * 请求成功
     */
    public static final int STATUS_SUCCESS = 200;
    /**
     * 测试
     */
    public static final int STATUS_TEST = 1;
    /**
     * 未登录
     */
    public static final int STATUS_NO_LOGIN = 2;
    /**
     * OrdersIDError
     */
    public static final int STATUS_ORDERSS_ID_ERROR = 3;
    /**
     * 参数错误
     */
    public static final int STATUS_PARAMETER_ERROR = 4;
    /**
     * 找不到接口
     */
    public static final int STATUS_NOT_FOUND_INTERFACE = 5;
    /**
     * 没有关键字
     */
    public static final int STATUS_NOT_KEYWORDS = 6;
    /**
     * 没有找到文件
     */
    public static final int STATUS_NO_FILE = 7;
    /**
     * 序列号错误
     */
    public static final int STATUS_SN_ERROR = 8;
    /**
     * 找不到产品
     */
    public static final int STATUS_NO_PRODUCT = 9;
    /**
     * 连接超时
     */
    public static final int STATUS_TIME_OUT = 10;
    /**
     * 没有Token
     */
    public static final int STATUS_NO_TOKEN = 11;
    /**
     * NotSupport
     */
    public static final int STATUS_NOT_SUPPORT = 12;
    /**
     * 没有邮箱
     */
    public static final int STATUS_NO_EMAIL = 13;
    /**
     * 登录错误
     */
    public static final int STATUS_LOGIN_ERROR = 14;
    /**
     * 订单错误 100
     */
    public static final int STATUS_Order_Error_100 = 15;
    /**
     * 订单错误 200
     */
    public static final int STATUS_Order_Error_200 = 16;
    /**
     * BlackList
     */
    public static final int STATUS_BLACK_LIST = 17;
    /**
     * 邮箱错误
     */
    public static final int STATUS_EMAIL_ERROR = 18;
    /**
     * 未注册
     */
    public static final int STATUS_NOT_REGISTER = 19;
    /**
     * Banned
     */
    public static final int STATUS_BANNED = 20;
    /**
     * 密码错误
     */
    public static final int STATUS_PASSWORD_ERROR = 21;
    /**
     * validateError
     */
    public static final int STATUS_VALIDATE_ERROR = 22;
    /**
     * OrdersIDError
     */
    public static final int STATUS_ORDERS_ID_ERROR = 23;
    /**
     * verifyFail
     */
    public static final int STATUS_VERIFY_FAIL = 24;
    /**
     * 已登录
     */
    public static final int STATUS_HAS_LOGGED = 25;
    /**
     * IllegalAccess
     */
    public static final int STATUS_ILLEGAL_ACCESS = 26;
    /**
     * 邮箱已被注册
     */
    public static final int STATUS_EMAIL_REPEAT_REGISTER = 27;
    /**
     * SecurityError
     */
    public static final int STATUS_SECURITY_ERROR = 28;
    /**
     * orderMax
     */
    public static final int STATUS_ORDERMAX = 29;
    /**
     * 询价
     */
    public static final int STATUS_IS_INQUIRY = 30;
    /**
     * paymentError
     */
    public static final int STATUS_PAYMENT_ERROR = 31;


    public static final String TIP_NO_NETWORK = "Network connection failed";
    public static final String TIP_TIMEOUT = "Connection timed out";
    public static final String TIP_UNKNOWN = "Connection error, please try again later";

}
