package com.demo.inspection.ui.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.inspection.R;
import com.demo.inspection.SystemAddActivity;
import com.demo.inspection.SystemDetailsActivity;
import com.demo.inspection.SystemListActivity;
import com.demo.inspection.bl.ComDef;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.MyHttp;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.ui.EquipmentActivity;

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
                    map.put("score", item.getString("score"));//获取你自己需要的字段
                    map.put("sysName", item.getString("sysName"));//获取你自己需要的字段
                    map.put("detial", item.getString("detial"));//获取你自己需要的字段
                    map.put("opttime", item.getString("opttime"));//获取你自己需要的字段
                    map.put("userId", item.getString("userId"));//获取你自己需要的字段
                    map.put("imageId",String.valueOf(R.drawable.ic_launcher));
                    list.add(map);
                }

                //刷新主界面
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] from = {"id","score", "sysName","opttime", "userId", "imageId"};  //决定提取哪些值来生成列表项
                        int[] to = {R.id.textViewInput2, R.id.textViewInput,
                                R.id.textViewOutput, R.id.textViewWeight,R.id.textViewAmountExercise, R.id.imageView2}; //决定填充哪些组建
                        SimpleAdapter adapter = new SimpleAdapter(getActivity(),list, R.layout.item_list, from, to);
                        listView.setAdapter(adapter);
                    }
                });





//                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                    @Override
//                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//
//                        new AlertDialog.Builder(getActivity())
//                                .setTitle("系统")
//                                .setItems(
//                                        R.array.Pos_items,
//                                        (dialog, which) -> {
//                                            if (which == 0) {//新增
//                                                Bundle bundle = new Bundle();
//                                                bundle.putString("id", item.getString("id"));
//                                                bundle.putString("sysName", item.getString("sys_name"));
//                                                bundle.putString("detial", item.getString("detial"));
//                                                bundle.putString("opttime", item.getString("opttime"));
//                                                bundle.putString("userId", item.getString("userId"));
//                                                Intent it1 = new Intent();
//                                                it1.putExtras(bundle);
//                                                it1.setClass(getActivity(), SystemDetailsActivity.class);
//                                                Log.i("id", id[i]);
//
//                                                startActivity(it1);
//
//
//                                            } else if (which == 1) {// 修改
//                                                Bundle bundle = new Bundle();
//                                                bundle.putString("id", item.getString("id"));
//                                                bundle.putString("sysName", item.getString("sys_name"));
//                                                bundle.putString("detial", item.getString("detial"));
//                                                bundle.putString("opttime", item.getString("opttime"));
//                                                bundle.putString("userId", item.getString("userId"));
//                                                Intent it1 = new Intent();
//                                                it1.putExtras(bundle);
//                                                it1.setClass(getActivity(), SystemAddActivity.class);
//                                                Log.i("id", id[i]);
//
//                                                startActivity(it1);
//                                            }else {  //删除
//                                                Bundle bundle = new Bundle();
//                                                bundle.putString("id", item.getString("id"));
//                                                bundle.putString("sysName", item.getString("sys_name"));
//                                                bundle.putString("detial", item.getString("detial"));
//                                                bundle.putString("opttime", item.getString("opttime"));
//                                                bundle.putString("userId", item.getString("userId"));
//                                                Intent it1 = new Intent();
//                                                it1.putExtras(bundle);
//                                                it1.setClass(getActivity(), SystemAddActivity.class);
//                                                Log.i("id", id[i]);
//
//                                                startActivity(it1);
//
//                                            }
//                                        }).show();
//                        return true;
//                    }
//                });


            }
        };





        return view;
    }

    public void imageViewonClick(View v) {


        System.out.println(listView.getPositionForView(v));
        Intent it = new Intent(getActivity(), SystemDetailsActivity.class);

        it.putExtra("id","1");
        it.putExtra("id","1");
        it.putExtra("id","1");
        it.putExtra("id","1");
        it.putExtra("id","1");

        startActivity(it);

    }

    public static SystemFragment getInstances(String name){
        SystemFragment systemFragment = new SystemFragment ();
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        systemFragment.setArguments(bundle);
        return  systemFragment;
    }
}
