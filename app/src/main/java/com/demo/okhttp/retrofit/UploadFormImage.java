package com.demo.okhttp.retrofit;


import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author 作者：张祥 on 2017/12/14 0014.
 *         邮箱：847874028@qq.com
 *         版本：v1.0
 *         功能：
 */

public class UploadFormImage {


    private static MultipartBody.Builder builder;

    /**
     * 压缩图片并生成上传图片的表单
     *
     * @param imageAddressList 选择的图片列表
     */
    public static List<MultipartBody.Part> createImageForm(List<File> imageAddressList) {
        //表单类型
        builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //多张图片
        for (int i = 0; i < imageAddressList.size(); i++) {
            //压缩后的图片
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageAddressList.get(i));
            builder.addFormDataPart("imgfile" + i, imageAddressList.get(i).getName(), imageBody);
        }
        return builder.build().parts();
    }


    /**
     * 上传单张图片的表单
     *
     * @param imageAddress 选择的图片
     */
    public static MultipartBody.Part createImageForm(File imageAddress) {
        if (imageAddress != null) {
            //表单类型
            builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            //多张图片
            //压缩后的图片
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageAddress);
            builder.addFormDataPart("imgfile", imageAddress.getName(), imageBody);
            return builder.build().parts().get(0);
        }
        return null;
    }

}
