package com.cocoon.jay.retrofituploadphotos.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.cocoon.jay.retrofituploadphotos.MyApplication;


/**
 * Created by Administrator on 2017/4/11.
 *
 * 避免重复点击效果叠加
 *
 */

public class ToastUtil {

    private static Toast toast;
    private Context context;
    public static void Toast(String content) {
        if(TextUtils.isEmpty(content)){
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(MyApplication.application,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
    public static void Toast(Context context , String content) {
        if(TextUtils.isEmpty(content)){
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context,
                    content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }


    public static void ToastFail(){

        if (toast == null) {
            toast = Toast.makeText(MyApplication.application,
                    "网络请求失败，请检查网络",
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText("网络请求失败，请检查网络");
        }
        toast.show();
    }

}
