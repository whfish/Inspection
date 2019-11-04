package com.demo.inspection.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.demo.inspection.R;
import com.demo.inspection.bl.ComDef;
import com.demo.inspection.bl.MyFragmentPagerAdapter;
import com.demo.inspection.bl.MyHttp;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.ui.Fragment.EquipmentFragment;
import com.demo.inspection.ui.Fragment.MeFragment;
import com.demo.inspection.ui.Fragment.StatusFragment;
import com.demo.inspection.ui.Fragment.SystemFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView mainBottom;
    ViewPager mainViewpage;

    private ArrayList<Fragment> fList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewpage = findViewById(R.id.main_viewpage);//ViewPager
        mainBottom = findViewById(R.id.main_bottom);//底部导航栏

        userFragmentPagerAdapter();

        mainBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.main_state:
                        mainViewpage.setCurrentItem(0);
                        break;
                    case R.id.main_dev:
                        mainViewpage.setCurrentItem(1);
                        break;
                    case R.id.main_sys:
                        mainViewpage.setCurrentItem(2);
                        break;
                    case R.id.main_my:
                        mainViewpage.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });

    }

    public void userFragmentPagerAdapter() {
        fList = new ArrayList<>();
        fList.add(StatusFragment.getInstances("状态"));
        fList.add(EquipmentFragment.getInstances("设备"));
        fList.add(SystemFragment.getInstances("系统"));
        fList.add(MeFragment.getInstances("我的"));
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(fList, getSupportFragmentManager());
        mainViewpage.setAdapter(adapter);
        mainViewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mainBottom.setSelectedItemId(R.id.main_state);
                        break;
                    case 1:
                        mainBottom.setSelectedItemId(R.id.main_dev);
                        break;
                    case 2:
                        mainBottom.setSelectedItemId(R.id.main_sys);
                        break;
                    case 3:
                        mainBottom.setSelectedItemId(R.id.main_my);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


}
