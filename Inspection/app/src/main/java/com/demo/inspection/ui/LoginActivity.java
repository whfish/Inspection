package com.demo.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.demo.inspection.R;
import com.demo.inspection.ui.Fragment.barchart.XgPwd;
import com.demo.inspection.utils.ComDef;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.ui.Fragment.MeFragment;
import com.demo.inspection.utils.ScreenUtil;
import com.demo.inspection.utils.ToastUtil;
import com.demo.inspection.utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Bundle bundle = new Bundle();
    private XgPwd xgPwd=new XgPwd();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ScreenUtil screenUtil=new ScreenUtil();
//        int dpi=screenUtil.getScreenDPI(this);
//        screenUtil.adapterScreen(this,dpi,false);

        setContentView(R.layout.activity_login);
        EditText account = findViewById(R.id.edt_login_account);
        EditText passwd = findViewById(R.id.edt_login_pwd);
        Button login = findViewById(R.id.tv_login);
        login.setOnClickListener((v) -> {
            if (!Tools.CheckHasValue(account)) {
                ToastUtil.toastCenter(this, "账号不能为空");
            } else if (!Tools.CheckHasValue(passwd)) {
                ToastUtil.toastCenter(this, "密码不能为空");
            } else {
                ReqParam req = new ReqParam();
                req.setUrl(ComDef.INTF_QUERYADMIN);//修改为实际接口
                HashMap map = new HashMap<String, String>();
                map.put(ComDef.QUERY_ACCOUNT, account.getText().toString());//修改为实际请求参数
                req.setMap(map);
                // 绑定用户名字，绑定在bundle里
                bundle.putString("username", account.getText().toString());

                new GetData(req,LoginActivity.this) {
                    @Override
                    public void dealResult(String result) throws JSONException {
                        if(result.equals("[null]")){
                            LoginActivity.this.runOnUiThread(() -> {
                                ToastUtil.toastCenter(LoginActivity.this, "用户不存在");
                            });
                        }else{
                            List<Map<String, String>> list = new ArrayList<>();
                            JSONArray array = new JSONArray(result);
                            JSONObject item = (JSONObject) array.get(0);
                            String dbpasswd = item.getString("password");
                            if( passwd.getText().toString().equals(dbpasswd)){
                                Intent intent = new Intent();
                                intent.putExtra("username", account.getText().toString());
                                intent.setClass(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                LoginActivity.this.runOnUiThread(() -> {
                                    ToastUtil.toastCenter(LoginActivity.this, "用户密码错误");
                                });
                            }
                        }


                    }
                };

            }//end else
        });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ScreenUtil screenUtil =new ScreenUtil();
        screenUtil.resetScreen(this);
    }

}
