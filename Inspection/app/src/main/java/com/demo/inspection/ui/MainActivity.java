package com.demo.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

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
        map.put(ComDef.QUERY_DATE,"2019-10-31");
//        map.put(ComDef.QUERY_INDEX,"1");//系统ID
        req.setMap(map);
        req.setUrl(ComDef.URL_PRE+ComDef.INTF_QUERYDEVICE);
        myHttp.requestOkHttpAsync(req, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i(ComDef.TAG, "请求okHttp失败:" + e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody rb = response.body();
                //直接获得应答字符串
                String result = rb.string();
                Log.i(ComDef.TAG, "原始返回:" + result);
                try {
                    list = myHttp.string2List(result);
                    Log.i(ComDef.TAG, "解析后返回:" + list.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    rb.close();
                }
            }
        });

    }


}
