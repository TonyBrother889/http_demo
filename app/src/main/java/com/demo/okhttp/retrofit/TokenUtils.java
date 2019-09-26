package com.demo.okhttp.retrofit;


import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by Administrator on 2019/1/8 0008.
 */

public class TokenUtils {

    private static TokenUtils ourInstance = new TokenUtils();

    public static TokenUtils getInstance() {
        return ourInstance;
    }

    private TokenUtils() {
    }


 /*   public String generateToken() {
        String code = String.valueOf(System.currentTimeMillis() + new Random().nextInt());

        String md5Token = MD5(code);
//        LogUtils.e("token", md5Token);
        if (Constant.IS_SERVER_RETURNED_VALUE) {
        //    Hawk.put(Constant.MD5, md5Token);
         //   LogUtils.e("token", md5Token);
            return md5Token;
        } else {
         //   String beforeMd5 = Hawk.get(Constant.MD5);
            return beforeMd5;
        }
        //不使用Base64的话会出现乱码。因为new String默认编码可能不能完全包含上面这个字节数组
        //base64将每三个字节转成4个字节，这样高位就使用00补齐，这样最大也就是63，最小为0。一共只有64种情况，就不会出现乱码了。
    }*/

    public final static String MD5(String s) {

        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

}
