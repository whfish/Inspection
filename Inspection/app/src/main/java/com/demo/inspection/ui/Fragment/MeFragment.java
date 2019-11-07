/*
 * 描述:
 *   实现我的页面基本功能
 *   2019-11
 */
package com.demo.inspection.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.demo.inspection.R;
import com.demo.inspection.utils.ComDef;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.ui.Fragment.barchart.XgPwd;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MeFragment extends Fragment implements View.OnClickListener {
    View view;
    static Bundle bundle = new Bundle();
    String dept, account, telphone, username;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_me, container, false);
        //跳转修改密码页面
        Button button = view.findViewById(R.id.xg_m);
        button.setOnClickListener((view) -> {
            Intent intent = new Intent(getActivity(), XgPwd.class);
            startActivity(intent);
        });

        TextView name = view.findViewById(R.id.uname);
        TextView uid = view.findViewById(R.id.uid);
        TextView phone = view.findViewById(R.id.uphone);
        TextView depart = view.findViewById(R.id.udepart);
        bundle = getArguments();
        // 与接口交互
        ReqParam req = new ReqParam();
        req.setUrl(ComDef.INTF_QUERYADMIN);
        HashMap map = new HashMap<String, String>();
        map.put(ComDef.QUERY_ACCOUNT, bundle.getString("username"));
        req.setMap(map);

       //  Log.i(ComDef.TAG, "我的开始查询");
        new GetData(req) {
            @Override
            public void dealResult(String result) throws JSONException {
                JSONArray array = new JSONArray(result);
                JSONObject item = (JSONObject) array.get(0);
                // 获取到需要数据
                dept = item.getString("dept");
                account = item.getString("account");
                username = item.getString("username");
                telphone = item.getString("phone");

                getActivity().runOnUiThread(() -> {
                    name.setText(username);
                    uid.setText(account);
                    phone.setText(telphone);
                    depart.setText(dept);
                });
            }
        };
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
