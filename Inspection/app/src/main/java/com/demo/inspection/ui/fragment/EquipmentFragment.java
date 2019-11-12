package com.demo.inspection.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.inspection.R;
import com.demo.inspection.ui.customview.MyListView;
import com.demo.inspection.utils.ComDef;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.ui.EquipmentDetailsActivity;
import com.demo.inspection.utils.ToastUtil;
import com.demo.inspection.utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EquipmentFragment extends Fragment {

    // 声明ListView控件
//    private ListView mListView;
    private MyListView mListView;
    int currPage = 1;//分页码
    //初始化一个item
    private JSONObject item;
    //初始化定义函数ReqParam
    ReqParam req = new ReqParam();
    //初始化map
    HashMap map = new HashMap<String, String>();
    //设置对应选项
    String str1 = "全部状态";
    String str2 = "正常";
    String str3 = "预警";
    String str4 = "告警";
    String str5 = "异常";
    //定义状态
    String[] Score = new String[]{str1, str2, str3, str4, str5};//1-正常，2-预警，3-告警,"null"-异常


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        ScreenUtil screenUtil=new ScreenUtil();
//        int dpi=screenUtil.getScreenDPI(this);
//        screenUtil.adapterScreen(this,dpi,false);

        View view = inflater.inflate(R.layout.activity_equipment, container, false);

        // 第一大块，创建ListView,设备页面列表
        mListView = view.findViewById(R.id.listView);

        //第二大块搜索，绑定搜索框
        EditText txt = view.findViewById(R.id.editText);
        //HashMap map= new HashMap<String,String>();
        //绑定搜索按钮
        Button btnGO = view.findViewById(R.id.buttonGO);
        btnGO.setOnClickListener(v -> {
            String key = txt.getText().toString();//获取文本内容
            if(key.equals("")){
                currPage = 1;
                mListView.clearflag();
                mListView.showLoading();
                query(null, currPage);
            }
            else if (Tools.CheckIP(key)) {
                currPage = 1;
                mListView.clearflag();
                mListView.showLoading();
                query(key, currPage);
            } else {
                ToastUtil.toastCenter(getActivity(),"输入IP格式错误！");
            }
        });
        //向下滚动执行方法
        mListView.setInterface(() -> {
            currPage++;
            String condition = txt.getText().toString();
            if (condition.equals("") || condition.equals("请输入IP进行查询")) {
                query(null, currPage);
            } else {
                query(condition, currPage);
            }

        });


        // Item点击时跳转到详情页
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int i, long l) {


                Intent intent = new Intent(getActivity(), EquipmentDetailsActivity.class); //参数1:Fragment所依存的Activity,参数2：要跳转的Activity
                Bundle bundle = new Bundle();

                TextView textIP = view.findViewById(R.id.textIP);
                String strIP = textIP.getText().toString();
                bundle.putString("IP", strIP);

                TextView textID = view.findViewById(R.id.tv_disabled);
                bundle.putString("devId", textID.getText().toString());
                intent.putExtras(bundle);
                getActivity().startActivity(intent); //这里一定要获取到所在Activity再startActivity()；

            }
        });


        currPage = 1;
        mListView.clearflag();
        mListView.showLoading();
        query(null, currPage);
        return view;
    }

    public static EquipmentFragment getInstances(String name) {
        EquipmentFragment equipmentFragment = new EquipmentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        equipmentFragment.setArguments(bundle);
        return equipmentFragment;
    }

    public void query(String condition, int currPage) {
        Log.i(ComDef.TAG, "查询第：" + currPage + "页");
        req.setUrl(ComDef.INTF_QUERYDEVICEPAGE);//连接到服务器，调用查询设备列表方法
        map.put(ComDef.QUERY_IP, condition);
        map.put(ComDef.PAGESIZE, String.valueOf(ComDef.QRY_NUM));
        map.put(ComDef.PAGENUM, String.valueOf(currPage));
        req.setMap(map);
        new GetData(req) {
            @Override
            public void dealResult(String result) throws JSONException {
                //刷新主界面
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        List<Map<String, String>> list = mListView.getEquList();
                        try {
                            JSONArray array = new JSONArray(result);
                            Log.i(ComDef.TAG, "查询到记录条数：" + array.length());
                            if (currPage == 1) {
                                //刷新查询
                                Log.i(ComDef.TAG, "刷新查询===");
                                list.clear();
                            } else {
                                //累加查询结果
                                Log.i(ComDef.TAG, "累加查询===");
                            }
                            for (int i = 0; i < array.length(); i++) {
                                item = (JSONObject) array.get(i);
                                Map<String, String> map = new HashMap<>();
                                map.put("id", item.getString("id"));
                                map.put("ip", item.getString("ip"));
                                //判断状态
                                switch (item.getString("score")) {
                                    case "1":
                                        map.put("score", str2);
                                        break;
                                    case "2":
                                        map.put("score", str3);
                                        break;
                                    case "3":
                                        map.put("score", str4);
                                        break;
                                    case "":
                                        map.put("score", str5);
                                }
                                map.put("errlist", item.getString("errlist"));
                                map.put("dev_Name", item.getString("devName"));
                                map.put("detail", item.getString("detail"));
                                map.put("sysname", item.getString("sysname"));
                                list.add(map);
                            }
                            Log.i(ComDef.TAG, "解析设备列表后返回:" + mListView.getEquList().toString());
                            mListView.getMyAdapter().notifyDataSetChanged();
                            mListView.loadComplete(array.length());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
    }
}
