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
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.MyHttp;
import com.demo.inspection.bl.ReqParam;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


    // 声明ListView控件
    private ListView mListView;
    //定义状态
    String[] Score = new String[]{"1", "2", "3"};//1-良好，2-告警，3-故障

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);

        //第一大块，设置下拉，准备适配器
        final ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Score);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //设置Spinner下拉列表
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                System.out.println("选择:" + adapter.getItem(position).toString());

                ReqParam req = new ReqParam();
                HashMap map= new HashMap<String,String>();
                String key = adapter.getItem(position).toString();//获取列表内容
                map.put(ComDef.QUERY_STATE,key);//用状态作为条件
                req.setMap(map);
                req.setUrl(ComDef.INTF_QUERYDEVICE);

                new GetData(req) {
                    @Override
                    public void dealResult(String result) throws JSONException {
                        List<Map<String, String>> Equlist = new ArrayList<>();
                        JSONArray array = new JSONArray(result);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject item = (JSONObject) array.get(i);
                            Map<String, String> map = new HashMap<>();
                            map.put("id", item.getString("id"));
                            map.put("ip", item.getString("ip"));
                            map.put("score", item.getString("score"));
                            map.put("errlist", item.getString("errlist"));
                            Equlist.add(map);
                        }
                        Log.i(ComDef.TAG, "解析后返回:" + Equlist.toString());
                        //刷新主界面
                        EquipmentActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String[] from = {"id", "ip", "score", "errlist"};  //决定提取哪些值来生成列表项
                                int[] to = {R.id.textName, R.id.textIP, R.id.textState, R.id.textEvent}; //对应到xml里的名字
                                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), Equlist, R.layout.activity_equipmentlist, from, to);
                                mListView.setAdapter(adapter);
                            }
                        });
                    }
                };

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        // 第二大块，创建ListView,设备页面列表
        mListView = findViewById(R.id.listView);
        ReqParam req = new ReqParam();

        req.setUrl(ComDef.INTF_QUERYDEVICE);//连接到服务器，调用查询设备列表方法
        new GetData(req) {
            @Override
            public void dealResult(String result) throws JSONException {
                List<Map<String, String>> Equlist = new ArrayList<>();
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = (JSONObject) array.get(i);
                    Map<String, String> map = new HashMap<>();
                    map.put("id", item.getString("id"));
                    map.put("ip", item.getString("ip"));
                    map.put("score", item.getString("score"));
                    map.put("errlist", item.getString("errlist"));
                    Equlist.add(map);
                }
                Log.i(ComDef.TAG, "解析后返回:" + Equlist.toString());
                //刷新主界面
                EquipmentActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] from = {"id", "ip", "score", "errlist"};  //决定提取哪些值来生成列表项
                        int[] to = {R.id.textName, R.id.textIP, R.id.textState, R.id.textEvent}; //对应到xml里的名字
                        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), Equlist, R.layout.activity_equipmentlist, from, to);
                        mListView.setAdapter(adapter);
                    }
                });
            }
        };

        //第三大块搜索，绑定搜索框
        EditText txt = findViewById(R.id.editText);
        HashMap map= new HashMap<String,String>();
        //绑定搜索按钮
        Button btnGO = findViewById(R.id.buttonGO);
        btnGO.setOnClickListener(v -> {
            String key = txt.getText().toString();//获取文本内容
            map.put(ComDef.QUERY_IP,key);//用IP作为条件
            req.setMap(map);
            req.setUrl(ComDef.INTF_QUERYDEVICE);

            new GetData(req) {
                @Override
                public void dealResult(String result) throws JSONException {
                    List<Map<String, String>> Equlist = new ArrayList<>();
                    JSONArray array = new JSONArray(result);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject item = (JSONObject) array.get(i);
                        Map<String, String> map = new HashMap<>();
                        map.put("id", item.getString("id"));
                        map.put("ip", item.getString("ip"));
                        map.put("score", item.getString("score"));
                        map.put("errlist", item.getString("errlist"));
                        Equlist.add(map);
                    }
                    Log.i(ComDef.TAG, "解析后返回:" + Equlist.toString());
                    //刷新主界面
                    EquipmentActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String[] from = {"id", "ip", "score", "errlist"};  //决定提取哪些值来生成列表项
                            int[] to = {R.id.textName, R.id.textIP, R.id.textState, R.id.textEvent}; //对应到xml里的名字
                            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), Equlist, R.layout.activity_equipmentlist, from, to);
                            mListView.setAdapter(adapter);
                        }
                    });
                }
            };
        });


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
