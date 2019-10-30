package com.demo.inspection.bl;

import android.util.Log;
import android.widget.SimpleAdapter;

import org.jetbrains.annotations.NotNull;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MyHttp {

    List<Map<String, String>> list = null;

    private void requestOkHttp(ReqParam reqParam) {
        OkHttpClient okHttpClient = new OkHttpClient();

        //Post
        FormBody.Builder fbody = new FormBody.Builder();
        HashMap<String,String> map = reqParam.getMap();
        if (map != null && !map.isEmpty())
            for (String key : map.keySet()) {
                if (map.get(key) != null && !(map.get(key).equals(""))){
                    fbody.add(key,map.get(key));
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
        Log.i(ComDef.TAG, "okHttp开始执行异步调用，进程:" + Thread.currentThread().getName());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i(ComDef.TAG, "请求okHttp失败:" + e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i(ComDef.TAG, "okHttp收到异步回调，进程:" + Thread.currentThread().getName());
                Log.i(ComDef.TAG, "请求okHttp成功！");
                //获得应答体
                ResponseBody rb = response.body();
                //直接获得应答字符串
                String result = rb.string();
                Log.i(ComDef.TAG, "请求结果:" + result);
                //解析字符串为对象
                try {
                    list = string2List(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    //应答体内包含输出流对象，不再使用需要关闭
                    rb.close();
                }
//                UrlSampleActivity.this.runOnUiThread(() -> {
//                    Log.i(SysCode.TAG, "2更新UI:" + Thread.currentThread().getName());
//                    String[] from = {"ID", "CDate", "Content", "UserID"};
//                    int[] to = {R.id.textViewID, R.id.textViewName, R.id.textViewAge, R.id.textViewUser};
//                    SimpleAdapter simpleAdapter = new SimpleAdapter(UrlSampleActivity.this, list, R.layout.mylist, from, to);
//                    listView.setAdapter(simpleAdapter);
//                });

            }
        });
    }

    //将响应流转换成字符串
    private String is2String(InputStream in) {
        try {
            InputStreamReader inr = new InputStreamReader(in, "utf-8");
            BufferedReader br = new BufferedReader(inr);
            String line = br.readLine();
            StringBuffer sb = new StringBuffer();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //解析字符串为对象
    private List<Map<String, String>> string2List(String in) throws JSONException {
        List<Map<String, String>> list = new ArrayList<>();
        JSONObject jb = new JSONObject(in);
        if (jb.has("Record")) {
            JSONArray array = jb.getJSONArray("Record");
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = (JSONObject) array.get(i);
                Map<String, String> map = new HashMap<>();
                map.put("ID", item.getString("ID"));
                map.put("CDate", item.getString("CDate"));
                map.put("Content", item.getString("Content"));
                map.put("UserID", item.getString("UserID"));
                list.add(map);
            }
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("ID", jb.getString("ID"));
            map.put("CDate", jb.getString("CDate"));
            map.put("Content", jb.getString("Content"));
            map.put("UserID", jb.getString("UserID"));
            list.add(map);
        }
        return list;
    }
}
