package com.demo.inspection.utils;

import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
    /**
     * Check ip.
     *
     * @param text :输入需要检测的文本（ip)
     *             注意：该正则表达式检测0-255的所有ip，不检测是否包含特殊ip
     */
    public static boolean CheckIP(String text) {
//        String money = text.getText().toString();
        Pattern p = Pattern.compile(ComDef.IP_NORMAL);
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
        if (e.getText().toString().isEmpty())
            return false;
        else
            return true;
    }

    //日期转换
    public static String myDateFormat(String opttime) throws JSONException {
        long time = new JSONObject(opttime).getLong("time");
        return new SimpleDateFormat(ComDef.DATEFORMAT).format(new Date(time));

    }

    //指标颜色判断
    public static void changeColor(JSONObject item, TextView v) throws JSONException {
        int data = Integer.valueOf(item.getString("data"));
        int rule = Integer.valueOf(item.getString("rule"));
        String name = item.getString("name");
        switch (name) {
            case "cpu":
            case "内存":
            case "磁盘":
                if (data > rule) {
                    v.setTextColor(ComDef.STATE_COLORS[3]);
                } else if (data == rule) {
                    v.setTextColor(ComDef.STATE_COLORS[2]);
                } else if (data < rule) {
                    v.setTextColor(ComDef.STATE_COLORS[1]);
                }
                break;
            default:
                if (data < rule) {
                    v.setTextColor(ComDef.STATE_COLORS[3]);
                } else if (data == rule) {
                    v.setTextColor(ComDef.STATE_COLORS[2]);
                } else if (data > rule) {
                    v.setTextColor(ComDef.STATE_COLORS[1]);
                }

        }


    }

}
