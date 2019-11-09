
/**
 * @ProjectName: Inspection
 * @Package: com.demo.inspection
 * @ClassName: SystemFragment
 * @Description:添加系统数据至系统列表
 * @Author: 张文普
 * @CreateDate: 2019/11/6 9:37
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/6 9:37
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

package com.demo.inspection;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.demo.inspection.utils.ComDef;
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
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


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

            finish();

        });

        Button buttonCancel = findViewById(R.id.buttonCanceS);
        buttonCancel.setOnClickListener((view) -> {
            finish ();

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
