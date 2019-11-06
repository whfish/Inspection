package com.demo.inspection;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.inspection.bl.ComDef;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.utils.ToastUtil;

import org.json.JSONException;

import java.util.HashMap;

public class SystemAddActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_systemadd);


        Button buttonok = findViewById(R.id.buttonOKS);
        buttonok.setOnClickListener((view) -> {
            ReqParam req = new ReqParam();
            req.setUrl(ComDef.INTF_SYSADD);//从INTF_SYSADD接口添加数据
            HashMap map1 = new HashMap<String, String>();

            EditText editTextADXTMCInput = findViewById(R.id.editTextADXTMC);
            map1.put(ComDef.SYS_NAME, editTextADXTMCInput.getText().toString()); //获取需要的字段：sysName

            EditText editTextADXTXQInput = findViewById(R.id.editTextADXTXQ);
            map1.put(ComDef.SYS_DETIAL, editTextADXTXQInput.getText().toString());//获取需要的字段：detial

            EditText editTextADOTInput = findViewById(R.id.editTextADOT);
            map1.put(ComDef.SYS_LINKMAN, editTextADOTInput.getText().toString());//获取需要的字段联系人


            EditText editTextADUseridInput = findViewById(R.id.editTextADUserid);
            map1.put(ComDef.SYS_PHONE, editTextADUseridInput.getText().toString());//获取你自己需要的字段联系人电话

            req.setMap(map1);

            new GetData(req) {
                @Override
                public void dealResult(String result) throws JSONException {
                    SystemAddActivity.this.runOnUiThread(() -> {
                        ToastUtil.toastCenter(SystemAddActivity.this, result);
                    });
                }

            };
//            new GetData(req) {
//                @Override
//                public void dealResult(String result) throws JSONException {
//
//                    List<Map<String, String>> list = new ArrayList<>();
//                    JSONArray array = new JSONArray(result);
//
//                    for (int i = 0; i < array.length(); i++) {
//
//                        JSONObject item = (JSONObject) array.get(i);
//                        Map<String, String> map = new HashMap<>();
//                        map.put("id", item.getString("id"));//获取需要的字段：id
//                        map.put("score", item.getString("score"));//获取需要的字段：score
//
//                        EditText editTextADXTMCInput = findViewById(R.id.editTextADXTMC);
//                        map.put("sysname", editTextADXTMCInput.getText().toString());//获取需要的字段：sysName
//                        Log.i("sysname----------", editTextADXTMCInput.getText().toString());
//
//                        EditText editTextADXTXQInput = findViewById(R.id.editTextADXTXQ);
//                        map.put("detial", editTextADXTXQInput.getText().toString());//获取需要的字段：detial
//                        Log.i("detial----------", editTextADXTXQInput.getText().toString());
//
//                        EditText editTextADOTInput = findViewById(R.id.editTextADOT);
//                        map.put("opttime", editTextADOTInput.getText().toString());//获取需要的字段：opttime
//
//                        EditText editTextADUseridInput = findViewById(R.id.editTextADUserid);
//                        map.put("userId", editTextADUseridInput.getText().toString());//获取你自己需要的字段：userId
//
//                        list.add(map);
//                    }
//                }
//            };

            finish ();

        });

        Button buttonCancel = findViewById(R.id.buttonCanceS);
        buttonCancel.setOnClickListener((view) -> {

        });




    }
}
