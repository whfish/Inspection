package com.demo.inspection.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.inspection.R;
import com.demo.inspection.bl.ComDef;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.utils.NetworkInformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquipmentDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_equipmentdetails);

        //androidx,必须使用getSupportActionBar，而不是getActionBar
        ActionBar actionBar = getSupportActionBar();
        // 显示返回按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
        // 去掉logo图标
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("返回");


        //绑定控件
        TextView textID = findViewById (R.id.textID);
        TextView textName = findViewById (R.id.textName);
        TextView textIP = findViewById (R.id.textIP);
        TextView textSystem = findViewById (R.id.textSystem);
        TextView textScore = findViewById (R.id.textScore);
        TextView textCpu = findViewById (R.id.textCpu);
        TextView textMemory = findViewById (R.id.textMemory);
        TextView textHard = findViewById (R.id.textHard);
        TextView textDetail = findViewById (R.id.textDetail);

        //取EquipmentFragment传过来的ip
        Bundle bundle = this.getIntent ().getExtras ();
        String ip = bundle.getString ("IP");


        //以ip为条件查询数据
        ReqParam req = new ReqParam();
        req.setUrl(ComDef.INTF_QUERYDEVICE);//修改为实际接口,查询设备详情
        HashMap map = new HashMap<String, String>();
        map.put(ComDef.QUERY_IP,ip);//修改为实际请求参数
        req.setMap(map);
        new GetData(req) {
            @Override
            public void dealResult(String result) throws JSONException {
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = (JSONObject) array.get(i);
                    //将查询到的数据写入页面控件
                    textID.setText (item.getString("id"));
                    textName.setText(item.getString("devName"));
                    textIP.setText (item.getString ("ip"));
                    textSystem.setText(item.getString("sysname"));
                    //判断状态
                    switch (item.getString("score")) {
                        case "1":
                            textScore.setText("正常");
                            break;
                        case "2":
                            textScore.setText("告警");
                            break;
                        case "3":
                            textScore.setText("预警");
                            break;
                        case "":
                            textScore.setText("异常");
                    }
                    textDetail.setText(item.getString("detail"));

                    //以id为条件查询设备的硬件状态
                    ReqParam req2 = new ReqParam();
                    req2.setUrl(ComDef.INTF_QUERYDEVICEDETAIL);//修改为实际接口,查询设备详情
                    HashMap map2 = new HashMap<String, String>();
                    String id = textID.getText().toString();//获取设备的id
                    Log.i(ComDef.TAG, "获取设备idxxxxxxxxxxxxx:" +  textID.getText().toString());
                    map2.put(ComDef.QUERY_INDEX,id);//修改为实际请求参数
                    req2.setMap(map2);
                    new GetData(req2) {
                        @Override
                        public void dealResult(String result) throws JSONException {
                            JSONArray array = new JSONArray(result);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject item = (JSONObject) array.get(i);
                                //判断硬件名称
                                switch (item.getString("name")) {
                                    case "cpu":
                                        textCpu.setText(item.getString("detail")+item.getString("rule")+item.getString("data_type"));
                                        break;
                                    case "内存":
                                        textMemory.setText(item.getString("detail")+item.getString("rule")+item.getString("data_type"));
                                        break;
                                    case "磁盘":
                                        textHard.setText(item.getString("detail")+item.getString("rule")+item.getString("data_type"));
                                        break;
                                }
                            }
                        }
                    };
                }
            }
        };



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:   //返回前一页
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
