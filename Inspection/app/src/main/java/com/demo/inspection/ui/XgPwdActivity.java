/*
 * 描述:
 *   实现密码修改基本功能
 *   2019-11
 */
package com.demo.inspection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
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

public class XgPwdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xgpwd);
        ActionBar actionBar = getSupportActionBar();
        // 显示返回按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
        // 去掉logo图标
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("返回");
        Bundle bundle = new Bundle();
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
                            String dbpasswd = item.getString("password");
                            XgPwdActivity.this.runOnUiThread(() -> {
                                if (dbpasswd.equals(xgpwd_edit_old.getText().toString())) {
                                    Update_Password(xgpwd_edit_check.getText().toString());
                                } else {
                                    ToastUtil.toastCenter(XgPwdActivity.this, "原密码输入错误！！");
                                }
                            });

                        }
                    };

                } else {
                    ToastUtil.toastCenter(this, "输入密码不一致！");
                }
            } else {
                ToastUtil.toastCenter(this, "用户不存在！");
            }
        });
        xgpwd_cancel.setOnClickListener(v -> {
            this.finish();
        });
    }

    ;

    // 修改密码
    private void Update_Password(String password) {
        ReqParam req1 = new ReqParam();
        req1.setUrl(ComDef.INTF_UPDATEADMIN);
        HashMap map1 = new HashMap<String, String>();
        map1.put(ComDef.QUERY_ACCOUNT, "admin");
        map1.put(ComDef.MODIFY_PW, password);
        req1.setMap(map1);
        // 获取后台修改提示数据，展示给前台
        new GetData(req1) {
            @Override
            public void dealResult(String result) throws JSONException {
                XgPwdActivity.this.runOnUiThread(() -> {
                    ToastUtil.toastCenter(XgPwdActivity.this, result + "请重新登陆！！！");
                    Intent login = new Intent();
                    login.setClass(XgPwdActivity.this, LoginActivity.class);
                    startActivity(login);

                });
            }
        };
    }

    // 返回
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



