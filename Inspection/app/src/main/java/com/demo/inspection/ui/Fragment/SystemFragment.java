package com.demo.inspection.ui.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.inspection.R;
import com.demo.inspection.bl.ComDef;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.MyHttp;
import com.demo.inspection.bl.ReqParam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SystemFragment extends Fragment {

    MyHttp myHttp = new MyHttp();
    List<Map<String, String>> list = null;
    private ListView listView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate (R.layout.activity_systemlist,container,false);

        listView =  view.findViewById(R.id.ListView);   //绑定xml
        ReqParam req = new ReqParam();
        req.setUrl(ComDef.INTF_QUERYSYS);//修改为实际接口
//        HashMap map = new HashMap<String, String>();
//        map.put(ComDef.QUERY_DEVINDEX, "2019-11-02");//修改为实际请求参数
//        map.put(ComDef.QUERY_STATE, "1");//修改为实际请求参数
//        req.setMap(map);
        new GetData(req) {
            @Override
            public void dealResult(String result) throws JSONException {
                List<Map<String, String>> list = new ArrayList<>();
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = (JSONObject) array.get(i);
                    Map<String, String> map = new HashMap<>();
                    map.put("id", item.getString("id"));//获取你自己需要的字段
                    map.put("sys_name", item.getString("sys_name"));//获取你自己需要的字段
//                    map.put("detial", item.getString("detial"));//获取你自己需要的字段
                    map.put("score", item.getString("score"));//获取你自己需要的字段
                    map.put("imageId",String.valueOf(R.drawable.ic_launcher));
                    list.add(map);
                }

                String[] from = {"id", "sys_name", "score", "imageId"};  //决定提取哪些值来生成列表项
                int[] to = {R.id.textViewInput2, R.id.textViewInput,
                        R.id.textViewOutput,  R.id.imageView2}; //决定填充哪些组建
                SimpleAdapter adapter = new SimpleAdapter(getActivity(),list, R.layout.item_list, from, to);
                listView.setAdapter(adapter);

            }
        };





        return view;
    }

    public static SystemFragment getInstances(String name){
        SystemFragment systemFragment = new SystemFragment ();
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        systemFragment.setArguments(bundle);
        return  systemFragment;
    }
}
