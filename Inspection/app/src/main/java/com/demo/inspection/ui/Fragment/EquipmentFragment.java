package com.demo.inspection.ui.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.inspection.R;
import com.demo.inspection.bl.ComDef;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.ui.EquipmentDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EquipmentFragment extends Fragment {

    // 声明ListView控件
    private ListView mListView;
    //初始化一个item
    private JSONObject item;
    //初始化定义函数ReqParam
    ReqParam req = new ReqParam();
    //初始化map
    HashMap map= new HashMap<String,String>();
    //定义状态
    String[] Score = new String[]{"1", "2", "3"};//1-良好，2-告警，3-故障
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate (R.layout.activity_equipment,container,false);

        // 第一大块，创建ListView,设备页面列表
        mListView = view.findViewById(R.id.listView);
        //ReqParam req = new ReqParam();

        req.setUrl(ComDef.INTF_QUERYDEVICE);//连接到服务器，调用查询设备列表方法
        new GetData(req) {
            @Override
            public void dealResult(String result) throws JSONException {
                List<Map<String, String>> Equlist = new ArrayList<>();
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    item = (JSONObject) array.get(i);
                    Map<String, String> map = new HashMap<>();
                    map.put("id", item.getString("id"));
                    map.put("ip", item.getString("ip"));
                    map.put("score", item.getString("score"));
                    map.put("errlist", item.getString("errlist"));
                    map.put("dev_name",item.getString("dev_name"));
                    map.put("detail",item.getString("detail"));
                    Equlist.add(map);
                }
                Log.i(ComDef.TAG, "解析后返回:" + Equlist.toString());
                //刷新主界面
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] from = {"id", "ip", "score", "errlist"};  //决定提取哪些值来生成列表项
                        int[] to = {R.id.textName, R.id.textIP, R.id.textState, R.id.textEvent}; //对应到xml里的名字
                        SimpleAdapter adapter = new SimpleAdapter(getActivity(), Equlist, R.layout.activity_equipmentlist, from, to);
                        mListView.setAdapter(adapter);
                    }
                });
            }
        };

        // Item点击时跳转到详情页
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public  void onItemClick(AdapterView<?> arg0, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), EquipmentDetailsActivity.class); //参数1:Fragment所依存的Activity,参数2：要跳转的Activity
                Bundle bundle = new Bundle();

                try {
                    bundle.putString("id", item.getString("id"));//获取你自己需要的字段
                    bundle.putString ("ip", item.getString("ip"));
                    bundle.putString ("score", item.getString("score"));
                    bundle.putString ("errlist", item.getString("errlist"));
                    bundle.putString ("dev_name",item.getString("dev_name"));
                    bundle.putString ("detail",item.getString("detail"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtras(bundle);
                getActivity().startActivity(intent); //这里一定要获取到所在Activity再startActivity()；

            }
        });


        //第二大块搜索，绑定搜索框
        EditText txt = view.findViewById(R.id.editText);
        //HashMap map= new HashMap<String,String>();
        //绑定搜索按钮
        Button btnGO = view.findViewById(R.id.buttonGO);
        btnGO.setOnClickListener(v -> {
            String key = txt.getText().toString();//获取文本内容
            map.put(ComDef.QUERY_IP,key);//用IP作为条件
            req.setMap(map);
            req.setUrl(ComDef.INTF_QUERYDEVICE);

            new GetData(req) {
                @Override
                public void dealResult(String result) throws JSONException {
                    List<Map<String, String>> Equlist = new ArrayList<>();
                    JSONArray array = new JSONArray(result);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject item = (JSONObject) array.get(i);
                        Map<String, String> map = new HashMap<>();
                        map.put("id", item.getString("id"));
                        map.put("ip", item.getString("ip"));
                        map.put("score", item.getString("score"));
                        map.put("errlist", item.getString("errlist"));
                        Equlist.add(map);
                    }
                    Log.i(ComDef.TAG, "解析后返回:" + Equlist.toString());
                    //刷新主界面
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String[] from = {"id", "ip", "score", "errlist"};  //决定提取哪些值来生成列表项
                            int[] to = {R.id.textName, R.id.textIP, R.id.textState, R.id.textEvent}; //对应到xml里的名字
                            SimpleAdapter adapter = new SimpleAdapter(getActivity(), Equlist, R.layout.activity_equipmentlist, from, to);
                            mListView.setAdapter(adapter);
                        }
                    });
                }
            };
        });

        //第三大块，设置下拉。      准备适配器
        final ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, Score);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //设置Spinner下拉列表
        Spinner spinner = view.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                System.out.println("选择:" + adapter.getItem(position).toString());

                ReqParam req = new ReqParam();
                HashMap map= new HashMap<String,String>();
                String key = adapter.getItem(position).toString();//获取列表内容
                map.put(ComDef.QUERY_STATE,key);//用状态作为条件
                req.setMap(map);
                req.setUrl(ComDef.INTF_QUERYDEVICE);

                new GetData(req) {
                    @Override
                    public void dealResult(String result) throws JSONException {
                        List<Map<String, String>> Equlist = new ArrayList<>();
                        JSONArray array = new JSONArray(result);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject item = (JSONObject) array.get(i);
                            Map<String, String> map = new HashMap<>();
                            map.put("id", item.getString("id"));
                            map.put("ip", item.getString("ip"));
                            map.put("score", item.getString("score"));
                            map.put("errlist", item.getString("errlist"));
                            Equlist.add(map);
                        }
                        Log.i(ComDef.TAG, "解析后返回:" + Equlist.toString());
                        //刷新主界面
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String[] from = {"id", "ip", "score", "errlist"};  //决定提取哪些值来生成列表项
                                int[] to = {R.id.textName, R.id.textIP, R.id.textState, R.id.textEvent}; //对应到xml里的名字
                                SimpleAdapter adapter = new SimpleAdapter(getActivity(), Equlist, R.layout.activity_equipmentlist, from, to);
                                mListView.setAdapter(adapter);
                            }
                        });
                    }
                };

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        return view;
    }

    public static EquipmentFragment getInstances(String name){
        EquipmentFragment equipmentFragment = new EquipmentFragment ();
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        equipmentFragment.setArguments(bundle);
        return  equipmentFragment;
    }
}
