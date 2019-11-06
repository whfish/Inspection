package com.demo.inspection;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.inspection.bl.ComDef;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.MyHttp;
import com.demo.inspection.bl.ReqParam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemDetailsActivity extends AppCompatActivity {

    MyHttp myHttp = new MyHttp();
    List<Map<String, String>> list = null;

    // 声明ListView控件
    private ListView mListView;
    //定义状态
    String[] Score = new String[]{"1-良好", "2-警告", "3-故障"};

    String string = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_systemdetails);

        Bundle bundle = this.getIntent().getExtras();
        String id = bundle.getString("id");
        string = bundle.getString("sysName");


        EditText editTextInput = findViewById(R.id.editTextInput);
        //       EditText editTextOutput = findViewById(R.id.editTextOutput);
        EditText editTextWeight = findViewById(R.id.editTextWeight);
//        EditText editTextAE = findViewById(R.id.editTextAmountExercise);

        editTextInput.setText(bundle.getString("sysName"));
        //       editTextOutput.setText(bundle.getString("opttime"));
        editTextWeight.setText(bundle.getString("opttime"));
        //     editTextAE.setText(bundle.getString("userId"));
        //       mListView = findViewById(R.id.ListView1);   //绑定xml

        ReqParam req = new ReqParam();
        req.setUrl(ComDef.INTF_QUERYSYS);//修改为实际接口


        /**
         *
         *
         */
        new GetData(req) {
            @Override
            public void dealResult(String result) throws JSONException {

                List<Map<String, String>> list = new ArrayList<>();
                JSONArray array = new JSONArray(result);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = (JSONObject) array.get(i);
                    Map<String, String> map = new HashMap<>();
                    map.put("id", item.getString("id"));//获取你自己需要的字段
                    map.put("score", item.getString("score"));//获取你自己需要的字段
                    map.put("sysname", item.getString("sysName"));//获取你自己需要的字段
                    map.put("detial", item.getString("detial"));//获取你自己需要的字段
                    map.put("opttime", item.getString("opttime"));//获取你自己需要的字段
                    map.put("userId", item.getString("userId"));//获取你自己需要的字段
                    //           //   map.put("imageId", String.valueOf(R.drawable.ic_launcher));
                    list.add(map);

                    for (Map<String, String> myitem : list) {
                        if (myitem.get("id").equals(id)) {
                            String score = myitem.get("score");
                            String sysName = myitem.get("sysname");
                            String detail = myitem.get("detial");
                            String userId = myitem.get("userId");

                            TextView js = findViewById(R.id.editTextOutput001);
                            js.setText(detail);

                            Log.i("cccccccccccc", sysName);
                            TextView uid = findViewById(R.id.editTextAmountExercise);
                            uid.setText(userId);

                        } else {


                        }

                    }


                }
            }


//
//
//        ReqParam req = new ReqParam();
//        req.setUrl(ComDef.URL_PRE+ComDef.INTF_QUERYDEVICE);//连接到服务器，调用查询设备列表方法
//        HashMap map = new HashMap<String, String>();
////        map.put(ComDef.QUERY_DATE, "2019-11-02");//修改为实际请求参数
////        map.put(ComDef.QUERY_INDEX, "1");//修改为实际请求参数
//        req.setMap(map);
//        new GetData(req) {
//            @Override
//            public void dealResult(String result) throws JSONException {
//                JSONArray array = new JSONArray(result);
//                List<Map<String, String>> list = new ArrayList<>();
//                for (int i = 0; i < array.length(); i++) {
//                    JSONObject item = (JSONObject) array.get(i);
//                    Map<String, String> map = new HashMap<>();
//                    map.put("id", item.getString("id"));//获取你自己需要的字段
//                    map.put("ip", item.getString("ip"));//获取你自己需要的字段
//                    map.put("devName", item.getString("devName"));//获取你自己需要的字段
//                    list.add(map);
//                }
//                Log.i(ComDef.TAG, "解析后返回:" + list.toString());
//
//                //刷新主界面
//                SystemDetailsActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        String[] from = {"id", "ip", "score", "errlist"};  //决定提取哪些值来生成列表项
//                        int[] to = {R.id.textName, R.id.textIP, R.id.textState, R.id.textEvent}; //对应到xml里的名字
//                        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), list, R.layout.activity_equipmentlist, from, to);
//                        mListView.setAdapter(adapter);
//                    }
//                });
//
//
//
//            }
        };

//
        //第二大块搜索，绑定搜索框
        mListView = findViewById(R.id.ListView1);   //绑定xml
        //初始化定义函数ReqParam
        ReqParam req1 = new ReqParam();
//初始化map
        HashMap map1 = new HashMap<String, String>();
//HashMap map= new HashMap<String,String>();


//        EditText txt = findViewById(R.id.editTextInput);
//
//            String key = txt.getText().toString();//获取文本内容


//        bundle.getString("sysName");//获取文本内容
        map1.put(ComDef.QUERY_INDEX, bundle.getString("id"));//用IP作为条件
        Log.i(ComDef.TAG, "查询条件-------------------------------------------:" + bundle.getString("id"));
        req1.setMap(map1);
        req1.setUrl(ComDef.INTF_QUERYDEVICE);

        /**
         *
         * 设备详情
         */
        new GetData(req1) {
            @Override
            public void dealResult(String result) throws JSONException {

                //s
                List<Map<String, String>> listS = new ArrayList<>();
                JSONArray arrayS = new JSONArray(result);
                //              List<Map<String, String>> EqulistNew = new ArrayList<>();

                for (int i = 0; i < arrayS.length(); i++) {
                    JSONObject item = (JSONObject) arrayS.get(i);
//                    Log.i("item-----------",item.getString("detial"));
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("id", item.getString("id"));//获取你自己需要的字段
                    map1.put("score", item.getString("score"));//获取你自己需要的字段
                    Log.i("item----------", item.getString("score"));
                    map1.put("sysname", item.getString("sysname"));//获取你自己需要的字段
                    Log.i("item----------", item.getString("detail"));
                    map1.put("detial", item.getString("detail"));//获取你自己需要的字段
                    map1.put("opttime", item.getString("opttime"));//获取你自己需要的字段
                    map1.put("userId", item.getString("userId"));//获取你自己需要的字段
                    //           //   map.put("imageId", String.valueOf(R.drawable.ic_launcher));

                    listS.add(map1);


//                    EditText txt = findViewById(R.id.editTextInput);
//
//                    String key = txt.getText().toString();//获取文本内容
//
//                    for (Map<String, String> sb : listS) {
//                        if (sb.get("sysname").equals(key)) {
//                            EqulistNew.add(sb);
//                        } else {
//
//                        }
//
//                    }
//                    EqulistNew.add(map);


                }

//                List<Map<String, String>> Equlist = new ArrayList<>();
//                JSONArray array = new JSONArray(result);
//                List<Map<String, String>> EqulistNew = new ArrayList<>();

//                for (int i = 0; i < array.length(); i++) {
//                    JSONObject item = (JSONObject) array.get(i);
//                    Map<String, String> map = new HashMap<>();
//                    map.put("id", item.getString("id"));
//                    map.put("ip", item.getString("ip"));
//                    map.put("score", item.getString("score"));
//                    map.put("errlist", item.getString("errlist"));
//                    Log.i("cccccccccccc", string);
//                    for (Map<String, String> sb : Equlist) {
//                        if (sb.get("sysname").equals(bundle.getString("sysName"))) {
//                            EqulistNew.add(sb);
//                        } else {
//
//                        }
//
//                    }
//                    Equlist.add(map);
//                }
//                Log.i(ComDef.TAG, "解析后返回:" + Equlist.toString());
                //刷新主界面
                SystemDetailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] from = {"id", "ip", "score", "errlist"};  //决定提取哪些值来生成列表项
                        int[] to = {R.id.textViewInput2, R.id.textViewInput,
                                R.id.textViewOutput, R.id.textViewWeight}; //对应到xml里的名字
                        SimpleAdapter adapter = new SimpleAdapter(SystemDetailsActivity.this, listS, R.layout.item_list1, from, to);
                        mListView.setAdapter(adapter);
                    }
                });
            }
        };

    }


}

