
/**
 * @ProjectName: Inspection
 * @Package: com.demo.inspection
 * @ClassName: SystemAddActivity
 * @Description:添加系统数据至系统列表
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.inspection.R;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.utils.ComDef;
import com.demo.inspection.utils.ToastUtil;

import org.json.JSONException;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SystemAddActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_systemadd);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        Button buttonOk = findViewById(R.id.buttonOKS);
        buttonOk.setOnClickListener((view) -> {
            ReqParam req1 = new ReqParam();
            req1.setUrl(ComDef.INTF_SYSADD);//从INTF_SYSMOD接口修改数据
            HashMap map1 = new HashMap<String, String>();

            //通过SYS_NAME接口修改linkmanphone数据
            EditText editTextADXTMCInput = findViewById(R.id.editTextName);
            EditText editTextADXTXQInput = findViewById(R.id.editTextDetial);
            EditText editTextADOTInput = findViewById(R.id.editTextLinkman);
            EditText editTextADUseridInput = findViewById(R.id.editTextPhone);

            String s = editTextADUseridInput.getText().toString();

            if (!editTextADXTMCInput.getText().toString().equals("")
                    && !editTextADXTXQInput.getText().toString().equals("")
                    && !editTextADOTInput.getText().toString().equals("")) {
                if (isPhoneNumberValid(s)) {

                    //通过SYS_NAME接口添加sysName数据

                    map1.put(ComDef.SYS_NAME, editTextADXTMCInput.getText().toString());

                    //通过SYS_DETIAL接口添加detail数据

                    map1.put(ComDef.SYS_DETIAL, editTextADXTXQInput.getText().toString());

                    //通过SYS_LINKMAN接口添加linkman数据

                    map1.put(ComDef.SYS_LINKMAN, editTextADOTInput.getText().toString());

                    //通过SYS_PHONE接口添加phone数据

                    map1.put(ComDef.SYS_PHONE, s);//获取需要的字段联系人电话
                    req1.setMap(map1);
                    new GetData(req1) {

                        @Override
                        public void dealResult(String result) throws JSONException {

                            SystemAddActivity.this.runOnUiThread(() -> {

                                ToastUtil.toastCenter(SystemAddActivity.this, result);
                                Log.i("ccccccccccccssssthg", result);
                            });


                        }
                    };

                    finish();

                } else {
                    editTextADUseridInput.setText("");
                    Toast.makeText(SystemAddActivity.this, "您输入的电话号码格式错误，请重新输入！", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(SystemAddActivity.this, "有值为空，请检查！", Toast.LENGTH_LONG).show();
            }
            Log.i("cccccccccccc15646984123", editTextADUseridInput.getText().toString());

        });

        Button buttonCancel = findViewById(R.id.buttonCancelS);
        buttonCancel.setOnClickListener((view) -> {
            finish();

        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {//返回前一页
            finish();
        }

        return true;
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;

        String expression01 = "^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
        String expression02 = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
        Pattern p01 = Pattern.compile(expression01);//通过Pattern对象将电话格式传入
        Matcher m01 = p01.matcher(phoneNumber);//检查电话号码的格式是否正确
        Pattern p02 = Pattern.compile(expression02);
        Matcher m02 = p02.matcher(phoneNumber);
        if (m01.matches() || m02.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
