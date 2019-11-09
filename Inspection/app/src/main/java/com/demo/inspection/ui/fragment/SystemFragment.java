
/**
 * @ProjectName: Inspection
 * @Package: com.demo.inspection
 * @ClassName: SystemFragment
 * @Description: 系统列表数据展示
 * @Author: 张文普
 * @CreateDate: 2019/11/6 9:37
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/6 9:37
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

package com.demo.inspection.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.demo.inspection.R;
import com.demo.inspection.ui.system.SystemAddActivity;
import com.demo.inspection.ui.system.SystemDetailsActivity;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.ui.customview.MyViewBinder;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.ui.system.SystemModActivity;
import com.demo.inspection.utils.ComDef;
import com.demo.inspection.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemFragment extends Fragment {

    //定义系统状态
    String str1 = "正常";
    String str2 = "预警";
    String str3 = "告警";
    String str4 = "异常";

    private ListView listView;

    public static SystemFragment getInstances(String name) {

        SystemFragment systemFragment = new SystemFragment();

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        systemFragment.setArguments(bundle);
        return systemFragment;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_systemlist, container, false);

        bindAdapter();


        listView = view.findViewById(R.id.ListView);//绑定xml


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), SystemDetailsActivity.class); //参数1:Fragment所依存的Activity,参数2：要跳转的Activity

                Bundle bundle = new Bundle();
                TextView one = view.findViewById(R.id.textViewSBid);
                String id = one.getText().toString();
                bundle.putString("id", id);

                TextView two = view.findViewById(R.id.textViewSBscore);
                String sysname = two.getText().toString();
                bundle.putString("sysName", sysname);

                TextView four = view.findViewById(R.id.textViewSBsysname);
                String opttime = four.getText().toString();
                bundle.putString("opttime", opttime);
                intent.putExtras(bundle);
                getActivity().startActivity(intent); //这里一定要获取到所在Activity再startActivity()；


            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("系统")
                        .setItems(
                                R.array.Pos_items,
                                (dialog, which) -> {
                                    if (which == 0) {//新增

                                        Intent intent = new Intent(getActivity(), SystemAddActivity.class); //参数1:Fragment所依存的Activity,参数2：要跳转的Activity


                                        Bundle bundle = new Bundle();
                                        TextView one = view.findViewById(R.id.textViewSBid);
                                        String id = one.getText().toString();
                                        bundle.putString("id", id);
                                        intent.putExtras(bundle);

                                        getActivity().startActivity(intent); //这里一定要获取到所在Activity再startActivity()；


                                    } else if (which == 1) {// 修改
                                        Intent intent = new Intent(getActivity(), SystemModActivity.class); //参数1:Fragment所依存的Activity,参数2：要跳转的Activity


                                        Bundle bundle = new Bundle();
                                        TextView one = view.findViewById(R.id.textViewSBid);
                                        String id = one.getText().toString();
                                        bundle.putString("id", id);
                                        intent.putExtras(bundle);
                                        getActivity().startActivity(intent); //这里一定要获取到所在Activity再startActivity()；

                                    } else {  //删除

                                        ReqParam req = new ReqParam();
                                        req.setUrl(ComDef.INTF_SYSDEL);//从INTF_SYSDEL接口删除数据
                                        HashMap map1 = new HashMap<String, String>();

                                        TextView one = view.findViewById(R.id.textViewSBid);
                                        String id1 = one.getText().toString();


                                        map1.put(ComDef.QUERY_SYSINDEX, id1); //获取需要的字段：sysName, editTextXTMCInput.getText().toString()); //获取需要的字段：sysName

                                        req.setMap(map1);

                                        new GetData(req) {
                                            @Override
                                            public void dealResult(String result) throws JSONException {
                                                getActivity().runOnUiThread(() -> {
                                                    ToastUtil.toastCenter(getActivity(), result);
                                                });
                                            }
                                        };
                                        bindAdapter();


                                    }
                                }).show();
                return true;
            }
        });

        return view;
    }


    private void bindAdapter() {
        ReqParam req = new ReqParam();
        req.setUrl(ComDef.INTF_QUERYSYS);//通过INTF_QUERYSYS接口查询系统列表详情

        new GetData(req) {
            @Override
            public void dealResult(String result) throws JSONException {
                List<Map<String, String>> list = new ArrayList<>();
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = (JSONObject) array.get(i);
                    Map<String, String> map = new HashMap<>();


                    System.out.println(item.getString("id"));

                    map.put("id", item.getString("id"));//获取你自己需要的字段
                    //                   map.put("score", item.getString("score"));//获取你自己需要的字段
                    //判断状态
                    switch (item.getString("score")) {
                        case "1":
                            map.put("score", str1);
                            break;
                        case "2":
                            map.put("score", str2);
                            break;
                        case "3":
                            map.put("score", str3);
                            break;
                        case "":
                            map.put("score", str4);
                    }
                    map.put("sysName", item.getString("sysName"));//获取你自己需要的字段
                    map.put("detial", item.getString("detial"));//获取你自己需要的字段
                    map.put("opttime", item.getString("opttime"));//获取你自己需要的字段
                    map.put("userId", item.getString("userId"));//获取你自己需要的字段
                    map.put("phone", item.getString("phone"));//获取你自己需要的字段：userId
                    map.put("linkman", item.getString("linkman"));//获取你自己需要的字段：userId
                    //           //   map.put("imageId", String.valueOf(R.drawable.ic_launcher));
                    list.add(map);
                }

                //刷新主界面
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] from = {"id", "score", "sysName", "linkman", "phone"};  //决定提取哪些值来生成列表项
                        int[] to = {R.id.textViewSBid, R.id.textViewSBip,
                                R.id.textViewSBscore, R.id.textViewSBsysname, R.id.textViewAmountExercise}; //决定填充哪些组建
                        SimpleAdapter adapter= new SimpleAdapter(getActivity(), list, R.layout.item_list, from, to);
                        adapter.setViewBinder(new MyViewBinder());
                        listView.setAdapter(adapter);
                    }
                });


            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        bindAdapter();
    }


}
