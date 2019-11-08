
/**
 * @ProjectName: Inspection
 * @Package: com.demo.inspection
 * @ClassName: SystemFragment
 * @Description: 详细系统数据展示
 * @Author: 张文普
 * @CreateDate: 2019/11/6 9:37
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/6 9:37
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

package com.demo.inspection;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.MyViewBinder;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.utils.ComDef;
import com.demo.inspection.utils.Tools;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemDetailsActivity extends AppCompatActivity {

    // 声明ListView控件
    private ListView mListView;
    //设置对应选项
    String str1 = "全部状态";
    String str2 = "正常";
    String str3 = "预警";
    String str4 = "告警";
    String str5 = "异常";
    //定义状态
    String[] Score = new String[]{str1,str2,str3,str4,str5};//1-正常，2-预警，3-告警,"null"-异常
    String data1;

    String string = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_systemdetails);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        /**
         *
         *取SystemListActivity传过来数据
         */

        Bundle bundle = this.getIntent().getExtras();
        String id = bundle.getString("id");
        string = bundle.getString("sysName");

        TextView editTextInput = findViewById(R.id.editTextInput);
        TextView opttime = findViewById(R.id.editTextWeight);
        TextView detial = findViewById(R.id.editTextOutput001);

        TextView linkman = findViewById(R.id.editTextAmountExercise);
        TextView phone = findViewById(R.id.editTextphone);

        editTextInput.setText(bundle.getString("sysName"));
      //  editTextWeight.setText(bundle.getString("opttime"));

        /**
         *
         *系统详情数据查询
         */

        ReqParam req = new ReqParam();
        HashMap map = new HashMap<String, String>();
        map.put(ComDef.QUERY_SYSINDEX,id);//用查询索引值作为条件
        Log.i(ComDef.TAG, "查询条件-------------------------------------------:" + bundle.getString("id"));
        req.setMap(map);
        req.setUrl(ComDef.INTF_QUERYSYSDETAIL );//从查询系统详情查询数据
        new GetData(req) {
            @Override
            public void dealResult(String result) throws JSONException {
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = (JSONObject) array.get(i);
                    //将查询到的数据写入页面控件

                   // DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                 //   data1 = format1.format(item.getString("opttime"));
                //    opttime.setText (data1);

                    String result1  = Tools.myDateFormat(item.getString("opttime"));

                    opttime.setText (result1);
                    Log.i("ccccccccccccsss", item.getString("opttime"));
                    detial.setText(item.getString("detial"));
                    linkman.setText (item.getString ("linkman"));
                    phone.setText(item.getString("phone"));

                }

//                List<Map<String, String>> list = new ArrayList<>();
//                JSONArray array = new JSONArray(result);
//
//                for (int i = 0; i < array.length(); i++) {
//
//                    JSONObject item = (JSONObject) array.get(i);
//                    Map<String, String> map = new HashMap<>();
//                    map.put("id", item.getString("id"));//获取需要的字段：id
//                    map.put("score", item.getString("score"));//获取需要的字段：score
//                    map.put("sysname", item.getString("sysName"));//获取需要的字段：sysName
//                    map.put("detial", item.getString("detial"));//获取需要的字段：detial
//                    map.put("opttime", item.getString("opttime"));//获取需要的字段：opttime
//                    map.put("userId", item.getString("userId"));//获取你自己需要的字段：userId
//                    list.add(map);
//
//                    //根据SystemListActivity传过来id查询系统数据
//                    for (Map<String, String> myitem : list) {
//                        if (myitem.get("id").equals(id)) {
//                            String score = myitem.get("score");
//                            String sysName = myitem.get("sysname");
//                            String detail = myitem.get("detial");
//                            String userId = myitem.get("userId");
//                            String opttime = myitem.get("opttime");
//
//                            //将查询系统数据绑定到控件显示
//
//                            TextView js = findViewById(R.id.editTextOutput001);
//                            js.setText(detail);
//
//                            Log.i("cccccccccccc", sysName);
//                            TextView uid = findViewById(R.id.editTextAmountExercise);
//                            uid.setText(userId);
//                            TextView editTextWeight = findViewById(R.id.editTextWeight);
//                            editTextWeight.setText(opttime);
//
//                        } else {
//                        }
//                    }
//                }
            }
        };

        /**
         *
         * 设备详情列表查询
         */

        mListView = findViewById(R.id.ListView1);   //绑定xml
        //初始化定义函数ReqParam
        ReqParam req1 = new ReqParam();
        //初始化map
        HashMap map1 = new HashMap<String, String>();

        //id作为查询条件，通过查询索引值接口查询设备列表

        map1.put(ComDef.QUERY_INDEX, bundle.getString("id"));//用查询索引值作为条件
        Log.i(ComDef.TAG, "查询条件---------:" + bundle.getString("id"));
        req1.setMap(map1);
        req1.setUrl(ComDef.INTF_QUERYDEVICE );

        new GetData(req1) {
            @Override
            public void dealResult(String result) throws JSONException {

                List<Map<String, String>> listS = new ArrayList<>();
                JSONArray arrayS = new JSONArray(result);

                for (int i = 0; i < arrayS.length(); i++) {
                    JSONObject item = (JSONObject) arrayS.get(i);
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("id", item.getString("id"));//获取你自己需要的字段
 //                   map1.put("score", item.getString("score"));//获取你自己需要的字段
                    switch (item.getString("score")) {
                        case "1":
                            map1.put("score", str2);
                            break;
                        case "2":
                            map1.put("score", str3);
                            break;
                        case "3":
                            map1.put("score", str4);
                            break;
                        case "":
                            map1.put("score", str5);
                    }
                    Log.i("item----------", item.getString("score"));
                    map1.put("sysname", item.getString("sysname"));//获取你自己需要的字段
                    Log.i("item----------", item.getString("detail"));
                    map1.put("detial", item.getString("detail"));//获取你自己需要的字段
                    map1.put("ip", item.getString("ip"));//获取你自己需要的字段
                    map1.put("opttime", item.getString("opttime"));//获取你自己需要的字段
                    map1.put("userId", item.getString("userId"));//获取你自己需要的字段
                    listS.add(map1);
                }

                //刷新设备列表界面
                SystemDetailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        String[] from = { "iP"};  //决定提取哪些值来生成列表项
//                        int[] to = { R.id.textid}; //对应到xml里的名字
                        String[] from = { "ip", "score", "sysname"};  //决定提取哪些值来生成列表项
                        int[] to = { R.id.textViewSBip1,
                                R.id.textViewSBscore1, R.id.textViewSBsysname1,}; //对应到xml里的名字
                        SimpleAdapter adapter = new SimpleAdapter(SystemDetailsActivity.this, listS, R.layout.item_list1, from, to);
                        adapter.setViewBinder(new MyViewBinder());
                        mListView.setAdapter(adapter);
                    }
                });
            }
        };

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {//返回前一页
            finish();
        }
        return true;
    }



}

