
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

package com.demo.inspection;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.utils.ComDef;
import com.demo.inspection.utils.ToastUtil;
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
         *系统列表详情数据查询
         */

        TextView name = findViewById(R.id.editTextSysName);
        TextView detial = findViewById(R.id.editTextSysDetial);
        TextView linkMan = findViewById(R.id.editTextSysLinkMan);
        TextView linkManPhone = findViewById(R.id.editTextSysLinkManPhone);

        ReqParam req = new ReqParam();
        HashMap map = new HashMap<String, String>();
        map.put(ComDef.QUERY_SYSINDEX, id);//用查询索引值作为条件
        Log.i(ComDef.TAG, "查询条件-------------------------------------------:" + id);
        req.setMap(map);
        req.setUrl(ComDef.INTF_QUERYSYSDETAIL);//从查询系统详情查询数据

        new GetData(req) {

            @Override
            public void dealResult(String result) throws JSONException {

                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = (JSONObject) array.get(i);

                    //将查询到的数据写入页面控件
                    name.setText(item.getString("sysName"));
                    detial.setText(item.getString("detial"));
                    linkMan.setText(item.getString("linkman"));
                    linkManPhone.setText(item.getString("phone"));

                }
            }
        };

        Button buttonOk = findViewById(R.id.buttonOK);
        buttonOk.setOnClickListener((view) -> {

            ReqParam req1 = new ReqParam();
            req1.setUrl(ComDef.INTF_SYSMOD);//从INTF_SYSMOD接口修改数据
            HashMap map1 = new HashMap<String, String>();

            //从QUERY_SYSINDEX接口传入修改数据的id值
            map1.put(ComDef.QUERY_SYSINDEX, id);
            //通过SYS_NAME接口修改sysName数据
            EditText editTextSysName = findViewById(R.id.editTextSysName);
            map1.put(ComDef.SYS_NAME, editTextSysName.getText().toString());
            Log.i("cccccccccccc", editTextSysName.getText().toString());

            //通过SYS_NAME接口修改detial数据
            EditText editTextSysDetial = findViewById(R.id.editTextSysDetial);
            map1.put(ComDef.SYS_DETIAL, editTextSysDetial.getText().toString());

            //通过SYS_NAME接口修改linkman数据
            EditText editTextSysLinkMan = findViewById(R.id.editTextSysLinkMan);
            map1.put(ComDef.SYS_LINKMAN, editTextSysLinkMan.getText().toString());

            //通过SYS_NAME接口修改linkmanphone数据
            EditText editTextSysLinkManPhone = findViewById(R.id.editTextSysLinkManPhone);
            map1.put(ComDef.SYS_PHONE, editTextSysLinkManPhone.getText().toString());//获取需要的字段联系人电话
            Log.i("cccccccccccc15646984123", editTextSysLinkManPhone.getText().toString());
            req1.setMap(map1);

            new GetData(req1) {

                @Override
                public void dealResult(String result) throws JSONException {

                    SystemModActivity.this.runOnUiThread(() -> {
                        ToastUtil.toastCenter(SystemModActivity.this, result);
                    });

                }
            };

            finish();
        });

        Button buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener((view) -> {

            finish();

        });

    }
}
