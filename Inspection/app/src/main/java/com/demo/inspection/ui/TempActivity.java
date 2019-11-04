package com.demo.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;

import com.demo.inspection.R;
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

public class TempActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        //跳转到状态页面
        Button buttonS = findViewById(R.id.buttonToStatus);
        buttonS.setOnClickListener((view) -> {
            Intent intent = new Intent();
            intent.setClass(this, StatusActivity.class);
            startActivity(intent);
        });

/**调用后台方法参考
 * 请安步骤修改为自己的方法
 */
        ReqParam req = new ReqParam();
        req.setUrl(ComDef.INTF_QUERYDEVICE);//修改为实际接口
        HashMap map = new HashMap<String, String>();
        map.put(ComDef.QUERY_DATE, "2019-11-02");//修改为实际请求参数
        map.put(ComDef.QUERY_INDEX, "1");//修改为实际请求参数
        req.setMap(map);
        new GetData(req) {
            @Override
            public void dealResult(String result) throws JSONException {
                List<Map<String, String>> list = new ArrayList<>();
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = (JSONObject) array.get(i);
                    Map<String, String> map = new HashMap<>();
                    map.put("id", item.getString("id"));//获取你自己需要的字段
                    map.put("ip", item.getString("ip"));//获取你自己需要的字段
                    map.put("devName", item.getString("devName"));//获取你自己需要的字段
                    list.add(map);
                }
                Log.i(ComDef.TAG, "解析后返回:" + list.toString());
            }
        };
    }
}
