package com.demo.inspection.utils;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Toast;


public class ToastUtil {
    /**
     * Toast center.
     *
     * @param a 显示页面的activity
     * @param s toast显示的提示字符串
     */
    public static void toastCenter(Activity a, String s) {
        ScreenUtil screenUtil=new ScreenUtil();
        screenUtil.resetScreen(a);
        Toast toast=Toast.makeText(a,s,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
