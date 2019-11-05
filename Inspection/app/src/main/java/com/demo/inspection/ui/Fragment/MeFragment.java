package com.demo.inspection.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.demo.inspection.R;
import com.demo.inspection.bl.ComDef;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.MyHttp;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.ui.Fragment.barchart.BarChart;
import com.demo.inspection.ui.Fragment.barchart.PieChart;
import com.demo.inspection.ui.Fragment.barchart.XgPwd;
import com.demo.inspection.ui.MainActivity;
import com.demo.inspection.ui.StatusActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.view.ColumnChartView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MeFragment extends Fragment implements View.OnClickListener{
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_me, container, false);

        Button button = view.findViewById (R.id.xg_m);
        button.setOnClickListener ((view) -> {
            Intent intent = new Intent(getActivity(),XgPwd.class);
            startActivity(intent);



            /*XgPwd xgPwd = new XgPwd ();
            Log.i("XXXX","tiaozhuan");

            getFragmentManager ().beginTransaction ().replace (R.id., xgPwd).addToBackStack (null).commit ();*/
        });
        TextView txtName = view.findViewById(R.id.uid);
        TextView name = view.findViewById(R.id.uname);
        TextView uid = view.findViewById(R.id.uid);
        TextView phone = view.findViewById(R.id.uphone);
        TextView depart = view.findViewById(R.id.udepart);



      /*  MyHttp myHttp = new MyHttp();
        List<Map<String, String>> list = null;
        ReqParam req = new ReqParam();
        HashMap map= new HashMap<String,String>();
*/

/*
        myHttp.requestOkHttpAsync(req, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i(ComDef.TAG, "请求okHttp失败:" + e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }

        });
*/
        //获取接口数据
       /* map.put(ComDef.INTF_QUERYADMIN,"xxx");
        req.setMap(map);
        //req.(ComDef.URL_PsetUrlRE+ComDef.INTF_QUERYADMIN);
*/

        //把数据存入User
        /*new GetData(req) {
            @Override
            public void dealResult(String result) throws JSONException {
                List<Map<String, String>> list = new ArrayList<>();
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = (JSONObject) array.get(i);
                    Map<String, String> map = new HashMap<>();
                    map.put("id", item.getString("id"));//获取你自己需要的字段
                    map.put("account", item.getString("account "));//获取你自己需要的字段
                    map.put("username", item.getString("username"));//获取你自己需要的字段
                    map.put("detail", item.getString("detail"));//获取你自己需要的字段
                    map.put("password", item.getString("password"));//获取你自己需要的字段
                    map.put("phone", item.getString("phone"));//获取你自己需要的字段
                    map.put("dept", item.getString("dept"));//获取你自己需要的字段
                    list.add(map);
                }
                Log.i(ComDef.TAG, "解析后返回:" + list.toString());
            }
        };
*/
        List<Map<String, String>> list = new ArrayList<>();

        Map<String, String> map = new HashMap<>();
        map.put("id", "00001");
        map.put("account", "admin");
        map.put("username", "管理员");
        map.put("phone", "13666666666");
        map.put("dept", "信息化");
        map.put("password", "000000");
        list.add(map);


        Log.i("XX", list.get(0).get("username"));

        String id = list.get(0).get("id");
        String username = list.get(0).get("username");
        String phones = list.get(0).get("phone");
        String dept = list.get(0).get("dept");
        String account = list.get(0).get("account");
        try {
            name.setText(username);
            uid.setText(id);
            phone.setText(phones);
            depart.setText(dept);
        } catch (Exception e) {
            Log.i("XX", "huoqushbai");
            e.getMessage();
        }

        return view;
    }


    public static MeFragment getInstances(String name) {
        MeFragment meFragment = new MeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        meFragment.setArguments(bundle);
        return meFragment;
    }


    @Override
    public void onClick(View view) {

    }
}
