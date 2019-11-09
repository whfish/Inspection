
/**
 * @ProjectName: Inspection
 * @Package: com.demo.inspection
 * @ClassName: SystemFragment
 * @Description: 修改系统列表数据
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.inspection.R;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.utils.ComDef;
import com.demo.inspection.utils.ToastUtil;
import com.demo.inspection.utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SystemModActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_systemmod);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = this.getIntent().getExtras();
        String id = bundle.getString("id");

        /**
         *
         *系统详情数据查询
         */

        TextView js = findViewById(R.id.editTextXTMC1);
        TextView uid = findViewById(R.id.editTextXTXQ1);
        TextView ot = findViewById(R.id.editTextOT1);
        TextView userid = findViewById(R.id.editTextUserid1);

        ReqParam req = new ReqParam();
        HashMap map = new HashMap<String, String>();
        map.put(ComDef.QUERY_SYSINDEX,bundle.getString("id"));//用查询索引值作为条件
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

                    js.setText(item.getString("sysName"));
                    uid.setText(item.getString("detial"));
                    String result1  = Tools.myDateFormat(item.getString("opttime"));
                    ot.setText (item.getString("linkman"));
                    userid.setText(item.getString("phone"));

                }
            }
        };

        Button buttonok = findViewById(R.id.buttonOK);
        buttonok.setOnClickListener((view) -> {
            ReqParam req1 = new ReqParam();
            req1.setUrl(ComDef.INTF_SYSMOD);//从INTF_SYSMOD接口修改数据
            HashMap map1 = new HashMap<String, String>();

            map1.put(ComDef.QUERY_SYSINDEX,bundle.getString("id")); //获取需要的字段：sysName, editTextXTMCInput.getText().toString()); //获取需要的字段：sysName

            EditText editTextXTMCInput = findViewById(R.id.editTextXTMC1);
            map1.put(ComDef.SYS_NAME, editTextXTMCInput.getText().toString()); //获取需要的字段：sysName
            Log.i("cccccccccccc", editTextXTMCInput.getText().toString());

            EditText editTextXTXQInput = findViewById(R.id.editTextXTXQ1);
            map1.put(ComDef.SYS_DETIAL, editTextXTXQInput.getText().toString());//获取需要的字段：detial

            EditText editTextOTInput = findViewById(R.id.editTextOT1);
            map1.put(ComDef.SYS_LINKMAN, editTextOTInput.getText().toString());//获取需要的字段联系人


           EditText editTextUseridInput = findViewById(R.id.editTextUserid1);
            map1.put(ComDef.SYS_PHONE, editTextUseridInput.getText().toString());//获取需要的字段联系人电话

            Log.i("cccccccccccc15646984123",editTextUseridInput.getText().toString());

            req1.setMap(map1);

            new GetData(req1) {
                @Override
                public void dealResult(String result) throws JSONException {
                    SystemModActivity.this.runOnUiThread(() -> {
                        ToastUtil.toastCenter(SystemModActivity.this, result);
                    });
                }
            };

            finish ();

        });

        Button buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener((view) -> {
            finish ();

        });

    }
}
