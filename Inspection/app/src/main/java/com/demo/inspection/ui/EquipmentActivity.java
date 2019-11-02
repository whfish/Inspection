package com.demo.inspection.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.inspection.R;
import com.demo.inspection.bl.ComDef;
import com.demo.inspection.bl.MyHttp;
import com.demo.inspection.bl.ReqParam;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class EquipmentActivity extends AppCompatActivity {
    MyHttp myHttp = new MyHttp();
    List<Map<String, String>> list = null;

    // 声明ListView控件
    private ListView mListView;
    //定义状态
    String[] Score = new String[]{"1-良好", "2-警告", "3-故障"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);

        //1、准备适配器
        final ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Score);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //2、设置Spinner下拉列表
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // 创建ListView,设备页面列表
        mListView = findViewById(R.id.listView);

        ReqParam req = new ReqParam();
        req.setUrl(ComDef.URL_PRE+ComDef.INTF_QUERYDEVICE);//连接到服务器，调用查询设备列表方法
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
                    list = myHttp.stringEquList(result);
                    Log.i(ComDef.TAG, "解析后返回: "+list.toString());

                    //刷新主界面
                    EquipmentActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String[] from = {"id", "ip", "score", "errlist"};  //决定提取哪些值来生成列表项
                            int[] to = {R.id.textName, R.id.textIP, R.id.textState, R.id.textEvent}; //对应到xml里的名字
                            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), list, R.layout.activity_equipmentlist, from, to);
                            mListView.setAdapter(adapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    rb.close();
                }
            }
        });

//        //绑定搜索框
//        EditText txt = findViewById(R.id.editText);
//        //绑定搜索按钮
//        Button btnGO = findViewById(R.id.buttonGO);
//        btnGO.setOnClickListener(v -> {
//
//            String key = txt.getText().toString();
//            if(key != null && !key.equals("")) {
//                //有条件查询
//                mData = mUserDAO.findByName(key);
//            }else{
//                //无条件查询
//                mData = mUserDAO.findAll();
//            }
//
//            // 绑定数据ListView
//            bindAdapter();
//        });


//        // Item点击时跳转详情页面
//        mListView.setOnItemClickListener((adapterView, view, i, l) -> {
//            Intent it = new Intent(this, EquipmentDetailsActivity.class);
//
//            User user = mData.get(i);
//
//            // 传递参数
//            it.putExtra("user", user);
//
//            startActivity(it);
//        });

    }


}
