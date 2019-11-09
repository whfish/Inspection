
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

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

public class SystemAddActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_systemadd);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Button butOk = findViewById(R.id.buttonOKS);
        butOk.setOnClickListener((view) -> {

            ReqParam req = new ReqParam();
            req.setUrl(ComDef.INTF_SYSADD);//从INTF_SYSADD接口添加数据
            HashMap map1 = new HashMap<String, String>();

            //通过SYS_NAME接口添加sysName数据
            EditText editTextADXTMCInput = findViewById(R.id.editTextName);
            map1.put(ComDef.SYS_NAME, editTextADXTMCInput.getText().toString());

            //通过SYS_DETIAL接口添加detail数据
            EditText editTextADXTXQInput = findViewById(R.id.editTextDetial);
            map1.put(ComDef.SYS_DETIAL, editTextADXTXQInput.getText().toString());

            //通过SYS_LINKMAN接口添加linkman数据
            EditText editTextADOTInput = findViewById(R.id.editTextLinkman);
            map1.put(ComDef.SYS_LINKMAN, editTextADOTInput.getText().toString());

            //通过SYS_PHONE接口添加phone数据
            EditText editTextADUseridInput = findViewById(R.id.editTextPhone);
            editTextADUseridInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence str, int arg1, int arg2, int arg3) { // 已经改变了的。
                    String contents = str.toString();
                    int length = contents.length();
                    if(length == 4){
                        if(contents.substring(3).equals(new String(" "))){ // -
                            contents = contents.substring(0, 3);
                            editTextADUseridInput.setText(contents);
                            editTextADUseridInput.setSelection(contents.length());
                        }else{ // +
                            contents = contents.substring(0, 3) + " " + contents.substring(3);
                            editTextADUseridInput.setText(contents);
                            editTextADUseridInput.setSelection(contents.length());
                        }
                    }
                    else if(length == 9){
                        if(contents.substring(8).equals(new String(" "))){ // -
                            contents = contents.substring(0, 8);
                            editTextADUseridInput.setText(contents);
                            editTextADUseridInput.setSelection(contents.length());
                        }else{// +
                            contents = contents.substring(0, 8) + " " + contents.substring(8);
                            editTextADUseridInput.setText(contents);
                            editTextADUseridInput.setSelection(contents.length());
                        }
                    }
                }
                @Override
                public void beforeTextChanged(CharSequence str, int arg1, int arg2,int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {// TODO Auto-generated method stub

                    map1.put(ComDef.SYS_PHONE, editTextADUseridInput.getText().toString());
                }
            });




            req.setMap(map1);

            new GetData(req) {

                @Override
                public void dealResult(String result) throws JSONException {

                    SystemAddActivity.this.runOnUiThread(() -> {
                        ToastUtil.toastCenter(SystemAddActivity.this, result);
                    });

                }
            };

            finish();
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
}
