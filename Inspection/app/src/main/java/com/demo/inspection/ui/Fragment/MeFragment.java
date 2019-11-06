package com.demo.inspection.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.inspection.R;
import com.demo.inspection.bl.ComDef;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.ui.Fragment.barchart.XgPwd;
import com.demo.inspection.utils.ScreenUtil;
import com.demo.inspection.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MeFragment extends Fragment implements View.OnClickListener {
    View view;
    static Bundle bundle = new Bundle();
    String dept, id, telphone, username;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        ScreenUtil screenUtil = new ScreenUtil();
//        int dpi = screenUtil.getScreenDPI(this);
//        screenUtil.adapterScreen(this, dpi, false);

        view = inflater.inflate(R.layout.activity_me, container, false);

        Button button = view.findViewById(R.id.xg_m);
        button.setOnClickListener((view) -> {
            Intent intent = new Intent(getActivity(), XgPwd.class);
            startActivity(intent);


        });
        TextView txtName = view.findViewById(R.id.uid);
        TextView name = view.findViewById(R.id.uname);
        TextView uid = view.findViewById(R.id.uid);
        TextView phone = view.findViewById(R.id.uphone);
        TextView depart = view.findViewById(R.id.udepart);
        bundle = getArguments();

        ReqParam req = new ReqParam();
        req.setUrl(ComDef.INTF_QUERYADMIN);//修改为实际接口
        HashMap map = new HashMap<String, String>();
        map.put(ComDef.QUERY_ACCOUNT, bundle.getString("username"));//修改为实际请求参数
        req.setMap(map);

        Log.i(ComDef.TAG, "我的开始查询");
        new GetData(req) {
            @Override
            public void dealResult(String result) throws JSONException {

                List<Map<String, String>> list = new ArrayList<>();
                JSONArray array = new JSONArray(result);
                JSONObject item = (JSONObject) array.get(0);
                dept = item.getString("dept");
                id = item.getString("id");
                username = item.getString("username");
                telphone = item.getString("phone");

                getActivity().runOnUiThread(() -> {
                    name.setText(username);
                    uid.setText(id);
                    phone.setText(telphone);
                    depart.setText(dept);


                });

            }


        };


//        bundle=getArguments();
//        map.put("id", "00001");//2
//        map.put("account", "admin");
//        map.put("username", bundle.getString("username"));//1
//        map.put("phone", "13666666666");//3
//        map.put("dept", "信息化");//4
//        map.put("password", "000000");//5
//        list.add(map);


//        Log.i("XX", list.get(0).get("username"));

//        String id = list.get(0).get("id");
//        String username = list.get(0).get("username");
//        String phones = list.get(0).get("phone");
//        String dept = list.get(0).get("dept");
//        String account = list.get(0).get("account");


        return view;
    }


    public static MeFragment getInstances(String name) {
        MeFragment meFragment = new MeFragment();

        bundle.putString("name", name);

        meFragment.setArguments(bundle);
        return meFragment;
    }


    @Override
    public void onClick(View view) {

    }

    public static MeFragment getUserName(String username) {
        MeFragment meFragment = new MeFragment();

        bundle.putString("username", username);

        meFragment.setArguments(bundle);
        return meFragment;

    }

}
