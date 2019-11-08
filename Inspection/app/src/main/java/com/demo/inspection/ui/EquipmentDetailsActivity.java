package com.demo.inspection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.inspection.R;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.utils.ComDef;
import com.demo.inspection.utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

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
        TextView textID = findViewById(R.id.textID);
        TextView textName = findViewById(R.id.textName);
        TextView textIP = findViewById(R.id.textIP);
        TextView textSystem = findViewById(R.id.textSystem);
        TextView textScore = findViewById(R.id.textScore);
        TextView textCpu = findViewById(R.id.textCpu);
        TextView textMemory = findViewById(R.id.textMemory);
        TextView textHard = findViewById(R.id.textHard);
        TextView textDetail = findViewById(R.id.textDetail);
        TextView textSevenDays = findViewById(R.id.qiri);

        //接收EquipmentFragment和StatusFragment传过来的ip
        Bundle bundle = this.getIntent().getExtras();
        String ip = bundle.getString("IP");

        //以ip为条件查询数据
        ReqParam req = new ReqParam();
        req.setUrl(ComDef.INTF_QUERYDEVICE);//修改为实际接口,查询设备详情
        HashMap map = new HashMap<String, String>();
        map.put(ComDef.QUERY_IP, ip);//修改为实际请求参数
        req.setMap(map);
        //刷新主界面
        EquipmentDetailsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new GetData(req) {
                    @Override
                    public void dealResult(String result) throws JSONException {
                        JSONArray array = new JSONArray(result);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject item = (JSONObject) array.get(i);
                            //将查询到的数据写入页面控件
                            textID.setText(item.getString("id"));
                            String id = textID.getText().toString();//取到写入的id 值，给查询设备硬件状态和七日情况传参使用
                            textName.setText(item.getString("devName"));
                            textIP.setText(item.getString("ip"));
                            textSystem.setText(item.getString("sysname"));
                            //判断状态
                            switch (item.getString("score")) {
                                case "1":
                                    textScore.setText("正常");
                                    textScore.setTextColor(ComDef.STATE_COLORS[1]);
                                    break;
                                case "2":
                                    textScore.setText("预警");
                                    textScore.setTextColor(ComDef.STATE_COLORS[2]);
                                    break;
                                case "3":
                                    textScore.setText("告警");
                                    textScore.setTextColor(ComDef.STATE_COLORS[3]);
                                    break;
                                case "":
                                    textScore.setText("异常");
                                    textScore.setTextColor(ComDef.STATE_COLORS[4]);
                            }
                            textDetail.setText(item.getString("detail"));

                            //以id为条件查询设备的硬件状态
                            ReqParam req2 = new ReqParam();
                            req2.setUrl(ComDef.INTF_QUERYDEVICEDETAIL);//修改为实际接口,查询设备详情
                            HashMap map2 = new HashMap<String, String>();
                            map2.put(ComDef.QUERY_INDEX, id);//修改为实际请求参数
                            req2.setMap(map2);
                            new GetData(req2) {
                                @Override
                                public void dealResult(String result) throws JSONException {
                                    JSONArray array = new JSONArray(result);
                                    int data;
                                    int rule;
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject item = (JSONObject) array.get(i);
                                        //判断硬件名称
                                        switch (item.getString("name")) {
                                            case "cpu":
                                                Tools.changeColor(item, textCpu);
                                                textCpu.setText(item.getString("detail") + item.getString("data") + item.getString("data_type"));
                                                break;
                                            case "内存":
                                                Tools.changeColor(item, textMemory);
                                                textMemory.setText(item.getString("detail") + item.getString("data") + item.getString("data_type"));
                                                break;
                                            case "磁盘":
                                                Tools.changeColor(item, textHard);
                                                textHard.setText(item.getString("detail") + item.getString("data") + item.getString("data_type"));
                                                break;
                                        }
                                    }
                                }
                            };

                            //查询最近七日情况
                            textSevenDays.setOnClickListener((v) -> {
                                Intent intent = new Intent(EquipmentDetailsActivity.this, DevStateActivity.class);
                                intent.putExtra("ip", ip);
                                intent.putExtra("devId", id);
                                startActivity(intent);
                            });
                        }
                    }
                };

            }
        });

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
