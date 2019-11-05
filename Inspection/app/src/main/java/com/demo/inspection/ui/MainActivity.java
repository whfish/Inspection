package com.demo.inspection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.demo.inspection.R;
import com.demo.inspection.bl.MyFragmentPagerAdapter;
import com.demo.inspection.ui.Fragment.EquipmentFragment;
import com.demo.inspection.ui.Fragment.MeFragment;
import com.demo.inspection.ui.Fragment.StatusFragment;
import com.demo.inspection.ui.Fragment.SystemFragment;
import com.demo.inspection.ui.NetWork.BaseActivity;
import com.demo.inspection.ui.NetWork.NetWorkActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    BottomNavigationView mainBottom;
    ViewPager mainViewpage;

    private ArrayList<Fragment> fList;
    private TextView mTv;
    private static final int NETWORK_ID = Menu.FIRST;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 设置网络连接提示
         */

//        mTv = (TextView) findViewById(R.id.warning);
//
//        //启动时判断网络状态
//        boolean netConnect = this.isNetConnect();
//        if (netConnect) {
//            mTv.setVisibility(View.GONE);
//        } else {
//            mTv.setVisibility(View.VISIBLE);
//        }




        /*
        * 底部导航菜单栏
        * */
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

    @Override
    public boolean onCreateOptionsMenu (Menu menu){

        menu.add(0, NETWORK_ID, 0, R.string.network);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected (MenuItem item){

        switch (item.getItemId()) {

            case NETWORK_ID:
                Intent netWork=new Intent(this, NetWorkActivity.class);
                startActivity(netWork);

                break;

        }

        return super.onOptionsItemSelected(item);
    }
//    @Override
//    public void onNetChange(int netMobile) {
//        super.onNetChange(netMobile);
//        //网络状态变化时的操作
//        if (netMobile== NetUtils.NETWORK_NONE){
//            mTv.setVisibility(View.VISIBLE);
//        }else {
//            mTv.setVisibility(View.GONE);
//        }
//    }


}
