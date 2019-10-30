package com.demo.inspection.utils;

import android.app.Activity;
import android.widget.Toast;


public class ToastUtil {
    public static void toastCenter(Activity a, String s) {
        Toast.makeText(a,s,Toast.LENGTH_SHORT).show();
    }
}
