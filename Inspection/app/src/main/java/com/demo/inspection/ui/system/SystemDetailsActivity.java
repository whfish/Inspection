
/**
 * @ProjectName: Inspection
 * @Package: com.demo.inspection
 * @ClassName: SystemDetailsActivity
 * @Description: 详细系统数据展示
 * @Author: 张文普
 * @CreateDate: 2019/11/6 9:37
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/6 9:37
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

package com.demo.inspection.ui.system;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.inspection.R;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.ui.customview.MyViewBinder;
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

    //设置状态
    String str1 = "正常";
    String str2= "预警";
    String str3 = "告警";
    String str4 = "异常";
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

        TextView editTextSysName1 = findViewById(R.id.editTextSysName1);
        editTextSysName1.setText(bundle.getString("sysName"));

        TextView editTextOptTime1 = findViewById(R.id.editTextOptTime1);
        TextView editTextDetial1 = findViewById(R.id.editTextDetial1);
        TextView editTextLinkMan1 = findViewById(R.id.editTextLinkMan1);
        TextView editTextPhone1 = findViewById(R.id.editTextPhone1);



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
                    String result1  = Tools.myDateFormat(item.getString("opttime"));
                    editTextOptTime1.setText (result1);
                    Log.i("ccccccccccccsss", item.getString("opttime"));
                    editTextDetial1.setText(item.getString("detial"));
                    editTextLinkMan1.setText (item.getString ("linkman"));
                    editTextPhone1.setText(item.getString("phone"));

                }
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
        map1.put(ComDef.QUERY_INDEX, id);//用查询索引值作为条件
        Log.i(ComDef.TAG, "查询条件---------:" + id);
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
                    map1.put("id", item.getString("id"));//获取需要的字段id
                    switch (item.getString("score")) {   //获取需要的字段score
                        case "1":
                            map1.put("score", str1);
                            break;
                        case "2":
                            map1.put("score", str2);
                            break;
                        case "3":
                            map1.put("score", str3);
                            break;
                        case "":
                            map1.put("score", str4);
                    }

                    Log.i("item----------", item.getString("score"));
                    map1.put("sysname", item.getString("sysname"));//获取你自己需要的字段
                    map1.put("ip", item.getString("ip"));//获取你自己需要的字段
                    listS.add(map1);
                }

                //刷新设备列表界面
                SystemDetailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String[] from = { "ip", "score", "sysname"};  //决定提取哪些值来生成列表项
                        int[] to = { R.id.textViewIp1,
                                R.id.textViewScore1, R.id.textViewSysname1,}; //对应到xml里的名字

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

