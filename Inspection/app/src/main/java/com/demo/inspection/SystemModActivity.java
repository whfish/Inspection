package com.demo.inspection;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.inspection.utils.ComDef;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemModActivity extends AppCompatActivity {

   // String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
    String EditText;

    String shoujihao ="^0?(13|14|15|18|17)[0-9]{9}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_systemmod);

        Bundle bundle = this.getIntent().getExtras();
        String id = bundle.getString("id");

        /**
         *
         *系统详情数据查询
         */


        ReqParam req = new ReqParam();
        req.setUrl(ComDef.INTF_QUERYSYS);//从查询系统列表查询数据
        new GetData(req) {
            @Override
            public void dealResult(String result) throws JSONException {

                List<Map<String, String>> list = new ArrayList<>();
                JSONArray array = new JSONArray(result);

                for (int i = 0; i < array.length(); i++) {

                    JSONObject item = (JSONObject) array.get(i);
                    Map<String, String> map = new HashMap<>();
                    map.put("id", item.getString("id"));//获取需要的字段：id
                    map.put("score", item.getString("score"));//获取需要的字段：score
                    map.put("sysname", item.getString("sysName"));//获取需要的字段：sysName
                    map.put("detial", item.getString("detial"));//获取需要的字段：detial
                    map.put("opttime", item.getString("opttime"));//获取需要的字段：opttime
                    map.put("userId", item.getString("userId"));//获取你自己需要的字段：userId
                    map.put("phone", item.getString("phone"));//获取你自己需要的字段：userId
                    map.put("linkman", item.getString("linkman"));//获取你自己需要的字段：userId
                    list.add(map);

                    //根据SystemListActivity传过来id查询系统数据
                    for (Map<String, String> myitem : list) {
                        if (myitem.get("id").equals(id)) {
                            String score = myitem.get("score");
                            String sysName = myitem.get("sysname");
                            String detail = myitem.get("detial");
                            String userId = myitem.get("userId");
                            String opttime = myitem.get("opttime");
                            String phone = myitem.get("phone");
                            String linkman = myitem.get("linkman");

                            //将查询系统数据绑定到控件显示

                            TextView js = findViewById(R.id.editTextXTMC1);
                            js.setText(sysName);

                            Log.i("cccccccccccc", sysName);
                            TextView uid = findViewById(R.id.editTextXTXQ1);
                            uid.setText(detail);

                            TextView ot = findViewById(R.id.editTextOT1);
                            ot.setText(linkman);

                            Log.i("cccccccccccc", linkman);
                            TextView userid = findViewById(R.id.editTextUserid1);
                            userid.setText(phone);
                            Log.i("cccccccccccc", phone);

                        } else {
                        }
                    }
                }
            }
        };



        EditText editTextUseridInput = findViewById(R.id.editTextUserid1);
        editTextUseridInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //输入文字前触发
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //text改变过程中，一般在此加入监听事件。
            }

            @Override
            public void afterTextChanged(Editable s) {


                if (editTextUseridInput.getText().toString().matches(shoujihao)){
                    EditText editText = findViewById(R.id.editTextUserid1);
                }else {
                    Toast.makeText(SystemModActivity.this, "输入电话号码不正确！", Toast.LENGTH_SHORT).show();
                }
                //输入后触发
            }
        });


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


  //         EditText editTextUseridInput = findViewById(R.id.editTextUserid1);
            map1.put(ComDef.SYS_PHONE, editTextUseridInput.getText().toString());//获取你自己需要的字段联系人电话





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




        Button buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener((view) -> {
            finish ();

        });

//        Button buttonOk = findViewById (R.id.buttonOK);
//        buttonOk.setOnClickListener ((view) -> {
//            user = dao.findById (id);
//            user.setInput (editTextInput.getText ().toString ());
//            user.setOutput (editTextOutput.getText ().toString ());
//            user.setWeight (editTextWeight.getText ().toString ());
//            user.setAmountExercise (editTextAE.getText ().toString ());
//            dao.modify (user);
//            setResult (RESULT_CODE_SUCCESS);
//            finish ();
//        });
//
//
//        Button buttonCancel = findViewById (R.id.buttonCancel);
//        buttonCancel.setOnClickListener ((view) -> {
//            setResult (RESULT_CODE_CANCEL);
//            finish ();
//        });





    }
}
