package com.demo.inspection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.inspection.R;
import com.demo.inspection.bl.ComDef;
import com.demo.inspection.bl.MyHttp;
import com.demo.inspection.bl.ReqParam;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MainActivity extends AppCompatActivity {
    MyHttp myHttp = new MyHttp();
    List<Map<String, String>> list = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //跳转到状态页面
        Button buttonS =findViewById (R.id.buttonToStatus);
        buttonS.setOnClickListener ((view)->{
            Intent intent =new Intent ();
            intent.setClass (this,StatusActivity.class);
            startActivity (intent);
        });




        ReqParam req = new ReqParam();
        HashMap map= new HashMap<String,String>();
        map.put(ComDef.QUERY_INDEX,"3");
        req.setMap(map);
        req.setUrl(ComDef.URL_PRE+ComDef.INTF_QUERYDEVICE);
        myHttp.requestOkHttpAsync(req, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i(ComDef.TAG, "请求okHttp失败:" + e);
        }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                Log.i(ComDef.TAG, "okHttp收到异步回调，进程:" + Thread.currentThread().getName());
//                Log.i(ComDef.TAG, "请求okHttp成功！");
                //获得应答体
                ResponseBody rb = response.body();
                //直接获得应答字符串
                String result = rb.string();
//                Log.i(ComDef.TAG, "请求结果:" + result);
//                System.out.print("请求结果:" + result);
                //解析字符串为对象
                try {
                    list = myHttp.string2List(result);
                    Log.i(ComDef.TAG, "解析后返回:" + list.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
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


}
