package com.demo.inspection.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.demo.inspection.R;
import com.demo.inspection.utils.ComDef;
import com.demo.inspection.bl.DealLineChart;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.ReqParam;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.LineData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DevStateActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mContainer;
    Spinner spinner;
    private LineChart lineChart;
    DealLineChart dealLineChart;
    List<CheckBox> mCheckBoxList;
    List<String> mItems;
    List<String> mMaxItems;
    Map<String, int[]> mData;
    TextView tvIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_state);
        tvIp = findViewById(R.id.tv_devstateip);
        lineChart = findViewById(R.id.lineChart);
        mContainer = findViewById(R.id.container);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Bundle bundle = this.getIntent().getExtras();
        String ip = bundle.getString("ip");
        String devId = bundle.getString("devId");//传值
        Log.i(ComDef.TAG, "onCreate: " + "设备id：" + devId);
        tvIp.setText("设备IP：" + ip);
        dealLineChart = new DealLineChart(this);
        dealLineChart.initLineChart(lineChart);
        setLineChartData(devId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ComDef.TAG, "onStart: ");
    }

    @Override
    public void onClick(View view) {
        int index = view.getId() - ComDef.PRE_INDEX;
        showLine(index);
    }

    private void setLineChartData(String devId) {
        //填充数据，在这里换成自己的数据源
        List<Integer> mList1 = new ArrayList<>();
        ReqParam req = new ReqParam();
        req.setUrl(ComDef.INTF_QUERYQRDD);//修改为实际接口
        HashMap map = new HashMap<String, String>();
        map.put(ComDef.QUERY_INDEX, devId);//修改为实际请求参数
        req.setMap(map);
        new GetData(req) {
            @Override
            public void dealResult(String result) throws JSONException {
                JSONArray array = new JSONArray(result);
                //获取最新一条记录的指标集合
                JSONObject newItem = (JSONObject) array.get(0);
                JSONArray newData = newItem.getJSONArray("data");
                mItems = new ArrayList<>();
                mMaxItems = new ArrayList<>();
                mData = new HashMap<>();
                for (int k = 0; k < newData.length(); k++) {
                    JSONObject o = (JSONObject) newData.get(k);
                    String keyName = o.getString("name");
                    mItems.add(keyName);
                    mMaxItems.add(o.getString("detail") + " (阀值：" + o.getString("rule") + o.getString("data_type") + ")");
                    int[] valueList = {0, 0, 0, 0, 0, 0, 0};
                    mData.put(keyName, valueList);
                }
                Log.i(ComDef.TAG, "解析后获得指标集合:" + mItems.toString());


                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = (JSONObject) array.get(i);
                    int index = item.getInt("id");
                    JSONArray data = item.getJSONArray("data");
                    for (int j = 0; j < data.length(); j++) {
                        JSONObject o = (JSONObject) data.get(j);
                        String keyName = o.getString("name");
                        int value = o.getInt("data");
                        mData.get(keyName)[index] = value;
                    }


                }
                Log.i(ComDef.TAG, "解析后获得指标值集合:" + mData.toString());

                DevStateActivity.this.runOnUiThread(() -> {
                    //动态添加checkBox
                    mCheckBoxList = new ArrayList<>();
                    for (int i = 0; i < mItems.size(); i++) {
                        CheckBox checkbox = new CheckBox(DevStateActivity.this);
                        checkbox.setId(ComDef.PRE_INDEX + i);
                        checkbox.setText(mMaxItems.get(i));
                        checkbox.setOnClickListener(DevStateActivity.this);
                        mCheckBoxList.add(checkbox);
                        mContainer.addView(checkbox);
                    }
                    for (int i = 0; i < mItems.size(); i++) {
                        String tag = mItems.get(i);
                        Log.i(ComDef.TAG, "dealResult: " + tag);
                        dealLineChart.createLine(mData.get(tag), tag, ComDef.MY_COLORS[i], lineChart);
                    }


                    LineData mLineData = lineChart.getLineData();
                    for (int i = 0; i < mLineData.getDataSetCount(); i++) {
                        mLineData.getDataSets().get(i).setVisible(false);
                    }
                    lineChart.notifyDataSetChanged(); //刷新数据
                    mCheckBoxList.get(0).setChecked(true);
                    showLine(0);
                });


            }
        };


    }


    /**
     * 功能：根据索引显示或隐藏指定线条
     */
    public void showLine(int index) {
        lineChart
                .getLineData()
                .getDataSets()
                .get(index)
                .setVisible(mCheckBoxList.get(index).isChecked());
        lineChart.invalidate();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {//返回前一页
            finish();
        }
        return true;
    }
}
