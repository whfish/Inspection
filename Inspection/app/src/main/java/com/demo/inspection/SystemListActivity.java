package com.demo.inspection;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


public class SystemListActivity extends Activity {
    private static String TAG = "MainActivity";
    private ListView listView;


    private String[] id = new String[]
            {"1", "2", "3", "4", "5"};

    private String[] input = new String[]
            {"OA系统", "bss", "客服", "经分", "网络侧"};
    private String[] output = new String[]
            {"OA系统", "bss", "客服", "经分", "网络侧"};
    private String[] weight = new String[]
            {"张三", "李四", "王五", "李二", "王二"};
    private String[] AE = new String[]
            {"张三", "李四", "王五", "李二", "王二"};




    int REQUEST_CODE_CREATE = 0;
    int REQUEST_CODE_MOD = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_systemlist);
        listView = findViewById(R.id.ListView); //绑定xml

        List<Map<String, String>> mapList = new ArrayList<>(); ////创建list集合,元素是Map

        for (int i = 0; i < id.length; i++) {

            Map<String, String> map = new HashMap<>();  //每个Map<String,?>对象生成一个列表项,即依次循环生成一个列表项

            map.put("id", id[i]);
            map.put("input", input[i]);
            map.put("output", output[i]);
            map.put("weight", weight[i]);
            map.put("AE", AE[i]);
            map.put("imageId",String.valueOf(R.drawable.ic_launcher));

            mapList.add(map);
        }

        String[] from = {"id", "input", "output", "weight", "AE", "imageId"};  //决定提取哪些值来生成列表项
        int[] to = {R.id.textViewInput2, R.id.textViewInput,
                R.id.textViewOutput, R.id.textViewWeight, R.id.textViewAmountExercise, R.id.imageView2}; //决定填充哪些组件
        SimpleAdapter adapter = new SimpleAdapter(this, mapList, R.layout.item_list, from, to);
        listView.setAdapter(adapter);


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int i, long arg3) {
//
//                Bundle bundle = new Bundle();
//                bundle.putString("id", id[i]);
//                bundle.putString("input", input[i]);
//                bundle.putString("output", output[i]);
//                bundle.putString("weight", weight[i]);
//                bundle.putString("AE", AE[i]);
//                Intent intent = new Intent();
//                intent.putExtras(bundle);
//                intent.setClass(SystemListActivity.this, SystemDetailsActivity.class);
//                Log.i("id", id[i]);
//                startActivity(intent);
//
//            }
//        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                new AlertDialog.Builder(SystemListActivity.this)
                        .setTitle("系统")
                        .setItems(
                                R.array.Pos_items,
                                (dialog, which) -> {



                                    if (which == 0) {//新增
                                        Bundle bundle = new Bundle();
                                        bundle.putString("id", id[i]);
                                        bundle.putString("input", input[i]);
                                        bundle.putString("output", output[i]);
                                        bundle.putString("weight", weight[i]);
                                        bundle.putString("AE", AE[i]);
                                        Intent it1 = new Intent();
                                        it1.putExtras(bundle);
                                        it1.setClass(SystemListActivity.this, SystemDetailsActivity.class);
                                        Log.i("id", id[i]);

                                        startActivity(it1);


                                    } else if (which == 1) {// 修改
                                        Bundle bundle = new Bundle();
                                        bundle.putString("id", id[i]);
                                        bundle.putString("input", input[i]);
                                        bundle.putString("output", output[i]);
                                        bundle.putString("weight", weight[i]);
                                        bundle.putString("AE", AE[i]);
                                        Intent it1 = new Intent();
                                        it1.putExtras(bundle);
                                        it1.setClass(SystemListActivity.this, SystemAddActivity.class);
                                        Log.i("id", id[i]);

                                        startActivity(it1);
                                    }else {  //删除
                                        Bundle bundle = new Bundle();
                                        bundle.putString("id", id[i]);
                                        bundle.putString("input", input[i]);
                                        bundle.putString("output", output[i]);
                                        bundle.putString("weight", weight[i]);
                                        bundle.putString("AE", AE[i]);
                                        Intent it1 = new Intent();
                                        it1.putExtras(bundle);
                                        it1.setClass(SystemListActivity.this, SystemAddActivity.class);
                                        Log.i("id", id[i]);

                                        startActivity(it1);

                                    }
                                }).show();
                return true;
            }
        });

    }
    public void imageViewonClick(View v) {


        System.out.println(listView.getPositionForView(v));
        Intent it = new Intent(SystemListActivity.this, SystemDetailsActivity.class);

        it.putExtra("id","1");
        it.putExtra("id","1");
        it.putExtra("id","1");
        it.putExtra("id","1");
        it.putExtra("id","1");

        startActivity(it);

    }




}