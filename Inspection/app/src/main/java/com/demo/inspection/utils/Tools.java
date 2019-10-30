package com.demo.inspection.utils;

import android.app.Activity;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
    public void CheckIP(EditText text,Activity a){
        String money = text.getText().toString();
        Pattern p = Pattern.compile("((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))");
        Matcher m = p.matcher(money);
        if (m.matches()) {


        } else {
            ToastUtil.toastCenter(a, "请输入正确的IP地址");
            text.setText("");
        }

    }

}
