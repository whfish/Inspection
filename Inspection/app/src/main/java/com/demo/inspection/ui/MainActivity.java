package com.demo.inspection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.demo.inspection.R;
import com.demo.inspection.ui.Fragment.SystemFragment;
import com.demo.inspection.ui.customview.adapter.MyFragmentPagerAdapter;
import com.demo.inspection.ui.fragment.EquipmentFragment;
import com.demo.inspection.ui.fragment.MeFragment;
import com.demo.inspection.ui.fragment.StatusFragment;
import com.demo.inspection.ui.network.BaseActivity;
import com.demo.inspection.ui.network.NetWorkActivity;
import com.demo.inspection.utils.ComDef;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static com.demo.inspection.utils.ComDef.TITLE_NAME;

public class MainActivity extends BaseActivity {

    BottomNavigationView mainBottom;
    ViewPager mainViewpage;
    String username;
    // 定义一个变量，来标识是否退出
    private boolean isExit = false;
    private ArrayList<Fragment> fList;
    private TextView mTv;
    private static final int NETWORK_ID = Menu.FIRST;
    private static final int Exit_ID = Menu.FIRST + 1;
    Bundle bundle = new Bundle ();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);

        setContentView (R.layout.activity_main);
        setTitle (TITLE_NAME[0]);

        Intent intent = getIntent ();
        // 获得登录页面的bundle(用户名)
        bundle = intent.getExtras ();
        username = bundle.getString ("username");

        mainViewpage = findViewById (R.id.main_viewpage);//ViewPager
        mainBottom = findViewById (R.id.main_bottom);//底部导航栏

        userFragmentPagerAdapter ();

        mainBottom.setOnNavigationItemSelectedListener (menuItem -> {

            switch (menuItem.getItemId ()) {

                case R.id.main_state:
                    mainViewpage.setCurrentItem (0);
                    break;
                case R.id.main_dev:
                    mainViewpage.setCurrentItem (1);
                    break;
                case R.id.main_sys:
                    mainViewpage.setCurrentItem (2);
                    break;
                case R.id.main_my:
                    mainViewpage.setCurrentItem (3);
                    break;

            }

            return true;
        });


    }


    public void userFragmentPagerAdapter() {

        fList = new ArrayList<> ();
        fList.add (StatusFragment.getInstances ("状态"));
        fList.add (EquipmentFragment.getInstances ("设备"));
        fList.add (SystemFragment.getInstances ("系统"));
        fList.add (MeFragment.getInstances ("我的"));
        // 把username 传给我的页面
        MeFragment.getUserName (username);

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter (fList, getSupportFragmentManager ());
        mainViewpage.setAdapter (adapter);
        mainViewpage.addOnPageChangeListener (new ViewPager.OnPageChangeListener () {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {

                    case 0:
                        mainBottom.setSelectedItemId (R.id.main_state);
                        setTitle (TITLE_NAME[0]);
                        break;
                    case 1:
                        mainBottom.setSelectedItemId (R.id.main_dev);
                        setTitle (TITLE_NAME[1]);
                        break;
                    case 2:
                        mainBottom.setSelectedItemId (R.id.main_sys);
                        setTitle (TITLE_NAME[2]);
                        break;
                    case 3:
                        mainBottom.setSelectedItemId (R.id.main_my);
                        setTitle (TITLE_NAME[3]);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add (0, NETWORK_ID, 0, R.string.network);
        menu.add (0, Exit_ID, 0, R.string.exit);

        return super.onCreateOptionsMenu (menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId ()) {

            case NETWORK_ID:
                Intent netWork = new Intent (this, NetWorkActivity.class);
                startActivity (netWork);

                break;
            case Exit_ID:
                System.exit (0);

        }

        return super.onOptionsItemSelected (item);
    }

    /**
     * 双击返回键退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (isExit) {
                this.finish ();

            } else {

                Toast.makeText (this, "再按一次退出", Toast.LENGTH_SHORT).show ();
                isExit = true;
                new Handler ().postDelayed (new Runnable () {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
            }
            return true;
        }

        return super.onKeyDown (keyCode, event);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(ComDef.TAG, "main-----onActivityResult执行！");



    }


}

