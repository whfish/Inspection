package com.demo.inspection.utils;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
    /**
     * Check ip.
     *
     * @param text :输入需要检测的文本（ip)
     *             注意：该正则表达式检测0-255的所有ip，不检测是否包含特殊ip

     */
    public boolean CheckIP(String  text) {
//        String money = text.getText().toString();
        Pattern p = Pattern.compile("((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))");
        Matcher m = p.matcher(text);
        if (m.matches()) {

            //匹配返回真值
            return true;


        } else {
            //格式不匹配返回false
            return false;
        }

    }

    public static boolean CheckHasValue(EditText e) {
        if(e.getText().toString().isEmpty())
            return false;
        else
            return true;
    }

}
