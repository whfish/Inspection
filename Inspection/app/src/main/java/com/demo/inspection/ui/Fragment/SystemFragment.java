package com.demo.inspection.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.demo.inspection.R;
import com.demo.inspection.SystemAddActivity;
import com.demo.inspection.SystemDetailsActivity;
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
    private static String TAG = "MainActivity";

    MyHttp myHttp = new MyHttp();
    List<Map<String, String>> list = null;
    private ListView listView;

    private ImageView btn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_systemlist, container, false);

        listView = view.findViewById(R.id.ListView);   //绑定xml
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
    //           //   map.put("imageId", String.valueOf(R.drawable.ic_launcher));
                    list.add(map);
                }

                //刷新主界面
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] from = {"id", "score", "sysName", "opttime"};  //决定提取哪些值来生成列表项
                        int[] to = {R.id.textViewSBid, R.id.textViewSBip,
                                R.id.textViewSBscore, R.id.textViewSBsysname}; //决定填充哪些组建
                        SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.item_list, from, to);
                        listView.setAdapter(adapter);
                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getActivity(), SystemDetailsActivity.class); //参数1:Fragment所依存的Activity,参数2：要跳转的Activity

                        Bundle bundle = new Bundle();
                        TextView one = view.findViewById(R.id.textViewSBid);
                        String id= one.getText().toString();
                        bundle.putString("id",id);

                        TextView two = view.findViewById(R.id.textViewSBscore);
                        String sysname= two.getText().toString();
                        bundle.putString("sysName",sysname);

                        TextView four = view.findViewById(R.id.textViewSBsysname);
                        String opttime=four.getText().toString();
                        bundle.putString("opttime",opttime);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent); //这里一定要获取到所在Activity再startActivity()；


                    }
                });
//
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public  void onItemClick(AdapterView<?> arg0, View view, int i, long l) {
//
//                        Intent intent = new Intent(getActivity(), SystemDetailsActivity.class); //参数1:Fragment所依存的Activity,参数2：要跳转的Activity
//                       //Bundle bundle = new Bundle();
//  //                      list=view.findViewById(android.R.id.list);
//
// //                       try {
// //                       TextView textPhone=(TextView) view.findViewById(R.id.);
//  //                      String phoneNew= textPhone.getText().toString();
//
//
//                            intent.putExtra("id", i);//获取你自己需要的字段
//                            intent.putExtra("sysName",i );//获取你自己需要的字段
//                            intent.putExtra("detial",i );//获取你自己需要的字段
//                            intent.putExtra("opttime",i );//获取你自己需要的字段
//                            intent.putExtra("userId",i );//获取你自己需要的字段
//                              startActivity(intent);
//                            bundle.putString("id",getOutput ().getString("id"));
//
//                            bundle.putString("score", item.getString("score"));
//                            bundle.putString("sysName", item.getString("sysName"));
//                            bundle.putString("detial", item.getString("detial"));
//                            bundle.putString("opttime", item.getString("opttime"));
//                            bundle.putString("userId", item.getString("userId"));
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        intent.putExtras(bundle);
//                        getActivity().startActivity(intent); //这里一定要获取到所在Activity再startActivity()；
//
             //       }
              //  });


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
                                                String id= one.getText().toString();
                                                bundle.putString("id",id);

                                                TextView two = view.findViewById(R.id.textViewSBscore);
                                                String sysname= two.getText().toString();
                                                bundle.putString("sysName",sysname);

                                                TextView four = view.findViewById(R.id.textViewSBsysname);
                                                String opttime=four.getText().toString();
                                                bundle.putString("opttime",opttime);
                                                intent.putExtras(bundle);
                                                getActivity().startActivity(intent); //这里一定要获取到所在Activity再startActivity()；


                                            } else if (which == 1) {// 修改
                                                Bundle bundle = new Bundle();
                                                bundle.putString("id", getString(Integer.parseInt("id")));//获取你自己需要的字段
                                                bundle.putString ("score",getString(Integer.parseInt("score")));
                                                bundle.putString ("sysName",getString(Integer.parseInt("sysName")));
                                                bundle.putString ("detial",getString(Integer.parseInt("detial")));
                                                bundle.putString ("opttime",getString(Integer.parseInt("opttime")));
                                                bundle.putString ("userId",getString(Integer.parseInt("opttime")));
                                                Intent it1 = new Intent();
                                                it1.putExtras(bundle);
                                                it1.setClass(getActivity(), SystemAddActivity.class);


                                                startActivity(it1);
                                            } else {  //删除
                                                Bundle bundle = new Bundle();
                                                bundle.putString("id", getString(Integer.parseInt("id")));//获取你自己需要的字段
                                                bundle.putString ("score",getString(Integer.parseInt("score")));
                                                bundle.putString ("sysName",getString(Integer.parseInt("sysName")));
                                                bundle.putString ("detial",getString(Integer.parseInt("detial")));
                                                bundle.putString ("opttime",getString(Integer.parseInt("opttime")));
                                                bundle.putString ("userId",getString(Integer.parseInt("userId")));
                                                Intent it1 = new Intent();
                                                it1.putExtras(bundle);
                                                it1.setClass(getActivity(), SystemAddActivity.class);;

                                                startActivity(it1);

                                            }
                                        }).show();
                        return true;
                    }
                });


                //item的点击事件，里面可以设置跳转并传值
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        if (view instanceof TextView) {
//                            System.out.println("点击文本");
//                        } else if (view instanceof ImageView) {
//                            System.out.println("点击图片");
//                        }
//                        Toast.makeText(getActivity(), "第" + i + "行", Toast.LENGTH_LONG).show();
//                    }
//                });


            }
        };


//        ImageView imageViewPie = view.findViewById (R.id.imageView2);
//        imageViewPie.setOnClickListener ((View view1) -> {
//
//            //传递参数
//            Intent intent=new Intent(getActivity(),SystemDetailsActivity.class); //参数1:Fragment所依存的Activity,参数2：要跳转的Activity
//            Bundle bundle = new Bundle();
//
//            try {
//                bundle.putString("id", item.getString("id"));//获取你自己需要的字段
//                bundle.putString ("score", item.getString("score"));
//                bundle.putString ("sysName",item.getString("sysName"));
//                bundle.putString ("detial",item.getString("detial"));
//                bundle.putString ("opttime",item.getString("opttime"));
//                bundle.putString ("userId",item.getString("userId"));
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//                                                bundle.putString ("score",getString(Integer.valueOf("score")));
//                                                bundle.putString ("sysName",getString(Integer.valueOf("sysName")));
//                                                bundle.putString ("detial",getString(Integer.valueOf("detial")));
//                                                bundle.putString ("opttime",getString(Integer.valueOf("opttime")));
//                                                bundle.putString ("userId",getString(Integer.valueOf("opttime")));
        //         intent.putExtras(bundle);
        //         getActivity().startActivity(intent); //这里一定要获取到所在Activity再startActivity()；


        //     });

        //     view = inflater.inflate(R.layout.item_list, null);

        //      btn=(ImageView) view.findViewById(R.id.imageView2);


        return view;


    }


//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        getActivity().setContentView(R.layout.item_list);
//        btn = (ImageView) getActivity().findViewById(R.id.imageView2);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "操纵取消！");
//            }
//        });
//    }


//        btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Log.i (TAG, "操纵取消！");
//            }
//        });


//        ImageView b1=(ImageView) getActivity().findViewById(R.id.imageView2);
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//                Intent intent=new Intent(getActivity(),SystemDetailsActivity.class);
//
//            }
//        });
    //   }


//    public void imageViewonClick(View v) {
//    Intent intent=new Intent(getActivity(),SystemDetailsActivity.class); //参数1:Fragment所依存的Activity,参数2：要跳转的Activity
//    Bundle bundle = new Bundle();
//
//    try {
//        bundle.putString("id", item.getString("id"));//获取你自己需要的字段
//        bundle.putString ("score", item.getString("score"));
//        bundle.putString ("sysName",item.getString("sysName"));
//        bundle.putString ("detial",item.getString("detial"));
//        bundle.putString ("opttime",item.getString("opttime"));
//        bundle.putString ("userId",item.getString("userId"));
//
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
//                                                intent.putExtras(bundle);
//    getActivity().startActivity(intent); //这里一定要获取到所在Activity再startActivity()；
//
//    }

//    public void imageViewonClick(View v) {
//
//
//        System.out.println(listView.getPositionForView(v));
//        Intent it = new Intent(getActivity(), SystemDetailsActivity.class);
//
//        it.putExtra("id","1");
//        it.putExtra("id","1");
//        it.putExtra("id","1");
//        it.putExtra("id","1");
//        it.putExtra("id","1");
//
//        startActivity(it);
//
//    }


    public static SystemFragment getInstances(String name) {
        SystemFragment systemFragment = new SystemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        systemFragment.setArguments(bundle);
        return systemFragment;
    }

//    @Override
//    public void onClick(View v) {
//        ImageView imageViewPie = v.findViewById (R.id.imageView2);
//        imageViewPie.setOnClickListener ((View view1) -> {
//
//            //传递参数
//            Intent intent=new Intent(getActivity(),SystemDetailsActivity.class); //参数1:Fragment所依存的Activity,参数2：要跳转的Activity
//            Bundle bundle = new Bundle();
//
//            try {
//                bundle.putString("id", item.getString("id"));//获取你自己需要的字段
//                bundle.putString ("score", item.getString("score"));
//                bundle.putString ("sysName",item.getString("sysName"));
//                bundle.putString ("detial",item.getString("detial"));
//                bundle.putString ("opttime",item.getString("opttime"));
//                bundle.putString ("userId",item.getString("userId"));
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//                                                bundle.putString ("score",getString(Integer.valueOf("score")));
//                                                bundle.putString ("sysName",getString(Integer.valueOf("sysName")));
//                                                bundle.putString ("detial",getString(Integer.valueOf("detial")));
//                                                bundle.putString ("opttime",getString(Integer.valueOf("opttime")));
//                                                bundle.putString ("userId",getString(Integer.valueOf("opttime")));
//                intent.putExtras(bundle);
//                 getActivity().startActivity(intent); //这里一定要获取到所在Activity再startActivity()；
//
//
//            });
//
//
//    }
}
