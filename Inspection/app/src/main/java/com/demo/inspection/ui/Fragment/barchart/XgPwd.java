/*
 * 描述:
 *   实现密码修改基本功能
 *   2019-11
 */
package com.demo.inspection.ui.Fragment.barchart;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.demo.inspection.R;
import com.demo.inspection.utils.ComDef;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.utils.ToastUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class XgPwd extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_xgpwd);
        ActionBar actionBar = getSupportActionBar();
        // 显示返回按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
        // 去掉logo图标
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("返回");
        Bundle bundle=new Bundle();
        TextView xgpwd_edit_name = findViewById(R.id.xgpwd_edit_name);
        TextView xgpwd_edit_old = findViewById(R.id.xgpwd_edit_old);
        TextView xgpwd_edit_new = findViewById(R.id.xgpwd_edit_new);
        TextView xgpwd_edit_check = findViewById(R.id.xgpwd_edit_check);
        Button xgpwd_sure = findViewById(R.id.xgpwd_sure);
        Button xgpwd_cancel = findViewById(R.id.xgpwd_cancel);
        //验证输入信息是否正确
        xgpwd_sure.setOnClickListener(v -> {
            if (xgpwd_edit_name.getText().toString().equals("admin")) {
                if (xgpwd_edit_check.getText().toString().equals(xgpwd_edit_new.getText().toString())) {
                        ReqParam req = new ReqParam();
                        HashMap map = new HashMap<String, String>();
                        map.put(ComDef.QUERY_ACCOUNT, "admin");
                        req.setUrl(ComDef.INTF_QUERYADMIN);
                        req.setMap(map);

                    new GetData(req) {
                        public void dealResult(String result) throws JSONException {
                            JSONArray array = new JSONArray(result);
                            JSONObject item = (JSONObject) array.get(0);
                             String dbpasswd= item.getString("password");
                            if (dbpasswd.equals(xgpwd_edit_old.getText().toString())) {
                                Update_Password(xgpwd_edit_check.getText().toString());

                            }else {
                                XgPwd.this.runOnUiThread(() -> {
                                    ToastUtil.toastCenter(XgPwd.this, "原密码输入错误！！");
                                });
                            }

                        }
                    };

                }else{
                    ToastUtil.toastCenter(this, "输入密码不一致！");
                }
            } else{
                ToastUtil.toastCenter(this, "用户不存在！");
            }
        });
        xgpwd_cancel.setOnClickListener(v -> {
            this.finish();
            });
        };
    private void Update_Password( String password) {
        ReqParam req1 = new ReqParam();
        req1.setUrl(ComDef.INTF_UPDATEADMIN);//修改为实际接口
        HashMap map1 = new HashMap<String, String>();
        map1.put(ComDef.QUERY_ACCOUNT, "admin");
        map1.put(ComDef.MODIFY_PW, password);//修改为实际请求参数
        req1.setMap(map1);
//                xgpwd_edit_check.getText().toString()
        new GetData(req1) {
            @Override
            public void dealResult(String result) throws JSONException {
                XgPwd.this.runOnUiThread(() -> {
                    ToastUtil.toastCenter(XgPwd.this, result);
                });
            }
        };
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:   //返回键的id
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
            }
        }
    }



