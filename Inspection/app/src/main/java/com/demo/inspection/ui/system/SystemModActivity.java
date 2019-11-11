
/**
 * @ProjectName: Inspection
 * @Package: com.demo.inspection
 * @ClassName: SystemModActivity
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
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.demo.inspection.R;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.utils.ComDef;
import com.demo.inspection.utils.ToastUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Log.i(ComDef.TAG, "查询条件------------------:" + id);
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

            //通过SYS_NAME接口修改linkmanphone数据
            EditText editTextSysLinkManPhone = findViewById(R.id.editTextSysLinkManPhone);
            EditText editTextSysName = findViewById(R.id.editTextSysName);
            EditText editTextSysDetial = findViewById(R.id.editTextSysDetial);
            EditText editTextSysLinkMan = findViewById(R.id.editTextSysLinkMan);
            String s=editTextSysLinkManPhone.getText().toString();

            if ( !editTextSysName.getText().toString().equals("")
            && !editTextSysDetial.getText().toString().equals("")
            && !editTextSysLinkMan.getText().toString().equals("")) {
                if (isPhoneNumberValid(s)) {

                    //从QUERY_SYSINDEX接口传入修改数据的id值
                    map1.put(ComDef.QUERY_SYSINDEX, id);

                    //通过SYS_NAME接口修改sysName数据
                    map1.put(ComDef.SYS_NAME, editTextSysName.getText().toString());
                    Log.i("修改的系统名称", editTextSysName.getText().toString());

                    //通过SYS_NAME接口修改detial数据
                    map1.put(ComDef.SYS_DETIAL, editTextSysDetial.getText().toString());

                    //通过SYS_NAME接口修改linkman数据
                    map1.put(ComDef.SYS_LINKMAN, editTextSysLinkMan.getText().toString());

                    //通过SYS_PHONE接口修改phone数据
                    map1.put(ComDef.SYS_PHONE, s);//获取需要的字段联系人电话
                    req1.setMap(map1);
                    new GetData(req1) {

                        @Override
                        public void dealResult(String result) throws JSONException {
                            SystemModActivity.this.runOnUiThread(() -> {
                                ToastUtil.toastCenter(SystemModActivity.this, result);
                                Log.i("返回结果result",result);
                            });
                        }

                    };

                    finish();

                } else {
                    editTextSysLinkManPhone.setText("");
                    Toast.makeText(SystemModActivity.this, "您输入的电话号码格式错误，请重新输入！", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(SystemModActivity.this, "有值为空，请检查！", Toast.LENGTH_LONG).show();
            }
            Log.i("获取的电话号码为：", editTextSysLinkManPhone.getText().toString());

        });

        Button buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener((view) -> {

            finish();

        });

    }

    public static boolean isPhoneNumberValid(String phoneNumber){

        boolean isValid=false;

        String expression01="^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
        String expression02="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
        Pattern p01=Pattern.compile(expression01);//通过Pattern对象将电话格式传入
        Matcher m01=p01.matcher(phoneNumber);//检查电话号码的格式是否正确
        Pattern p02=Pattern.compile(expression02);
        Matcher m02=p02.matcher(phoneNumber);

        if(m01.matches()||m02.matches()){
            isValid=true;
        }

        return isValid;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {//返回前一页
            finish();
        }

        return true;
    }

}
