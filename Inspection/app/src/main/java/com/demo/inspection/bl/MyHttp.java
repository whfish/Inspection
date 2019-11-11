package com.demo.inspection.bl;

import android.util.Log;

import com.demo.inspection.utils.ComDef;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @Path: com.demo.inspection.bl.MyHttp
 * @Description: java类作用描述
 * @Author: 王欢
 * @CreateDate: 2019/10/30 11:25
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/30 11:25
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MyHttp {

    public String requestOkHttpSync(ReqParam reqParam) {
        /**
         * @method requestOkHttpSync
         * @description 同步调用
         * @date: 2019/10/30 14:46
         * @author: 王欢
         * @param [reqParam]
         * @return void
         */
        OkHttpClient okHttpClient = new OkHttpClient();
        //Post
        FormBody.Builder fbody = new FormBody.Builder();
        HashMap<String, String> map = reqParam.getMap();
        if (map != null && !map.isEmpty())
            for (String key : map.keySet()) {
                if (map.get(key) != null && !(map.get(key).equals(""))) {
                    fbody.add(key, map.get(key));
                }
            }

        RequestBody body = fbody.build();
        Request request = new Request.Builder()
                .url(reqParam.getUrl())
                .post(body)
                .build();
        //创建Call对象
        Call call = okHttpClient.newCall(request);
        System.out.print("okHttp开始执行同步调用");
        ResponseBody rb = null;
        String result = "";
        try {
            rb = call.execute().body();
            result = rb.string();
            System.out.print("请求结果:" + result);
        } catch (Exception e) {
            System.out.print("异常：" + e);
        } finally {
            //应答体内包含输出流对象，不再使用需要关闭
            if (rb != null) {
                rb.close();
            }
        }
        return result;

    }

    public void requestOkHttpAsync(ReqParam reqParam, Callback callback) {
        /**
         * @method requestOkHttpAsync
         * @description 描述一下方法的作用
         * @date: 2019/10/30 16:45
         * @author: 王欢
         * @param [reqParam, callback：回调函数]
         * @return void
         */

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(ComDef.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(ComDef.REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .build();

        //Post
        FormBody.Builder fbody = new FormBody.Builder();
        HashMap<String, String> map = reqParam.getMap();
        if (map != null && !map.isEmpty())
            for (String key : map.keySet()) {
                if (map.get(key) != null && !(map.get(key).equals(""))) {
                    fbody.add(key, map.get(key));
                }
            }

        RequestBody body = fbody.build();
        Request request = new Request.Builder()
                .url(reqParam.getUrl())
                .post(body)
                .build();
        //创建Call对象
        Call call = okHttpClient.newCall(request);
        //执行异步调用
        Log.i(ComDef.TAG, "okHttp开始执行异步调用，url:" + reqParam.getUrl());
//        System.out.print("okHttp开始执行异步调用，进程:" + Thread.currentThread().getName());

        call.enqueue(callback);

    }

    public List<Map<String, String>> string2List(String in) throws JSONException {
        List<Map<String, String>> list = new ArrayList<>();
        JSONArray array = new JSONArray(in);
        for (int i = 0; i < array.length(); i++) {
            JSONObject item = (JSONObject) array.get(i);
            Map<String, String> map = new HashMap<>();
            map.put("id", item.getString("id"));
            map.put("ip", item.getString("ip"));
            map.put("devName", item.getString("devName"));
            list.add(map);
        }
        return list;
    }

    public List<Map<String, String>> stringEquList(String in) throws JSONException {
        List<Map<String, String>> Equlist = new ArrayList<>();
        JSONArray array = new JSONArray(in);
        for (int i = 0; i < array.length(); i++) {
            JSONObject item = (JSONObject) array.get(i);
            Map<String, String> map = new HashMap<>();
            map.put("id", item.getString("id"));
            map.put("ip", item.getString("ip"));
            map.put("score", item.getString("score"));
            map.put("errlist", item.getString("errlist"));
            Equlist.add(map);
        }
        return Equlist;
    }
}
