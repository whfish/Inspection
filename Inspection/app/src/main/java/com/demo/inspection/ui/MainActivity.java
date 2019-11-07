package com.demo.inspection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.demo.inspection.R;
import com.demo.inspection.SystemAddActivity;
import com.demo.inspection.SystemModActivity;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.MyFragmentPagerAdapter;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.ui.Fragment.EquipmentFragment;
import com.demo.inspection.ui.Fragment.MeFragment;
import com.demo.inspection.ui.Fragment.StatusFragment;
import com.demo.inspection.ui.Fragment.SystemFragment;
import com.demo.inspection.ui.NetWork.BaseActivity;
import com.demo.inspection.ui.NetWork.NetWorkActivity;
import com.demo.inspection.utils.ComDef;
import com.demo.inspection.utils.ToastUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

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
    Bundle bundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        setTitle(TITLE_NAME[0]);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        username = bundle.getString("username");




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
        MeFragment.getUserName(username);

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
                        setTitle(TITLE_NAME[0]);
                        break;
                    case 1:
                        mainBottom.setSelectedItemId(R.id.main_dev);
                        setTitle(TITLE_NAME[1]);
                        break;
                    case 2:
                        mainBottom.setSelectedItemId(R.id.main_sys);
                        setTitle(TITLE_NAME[2]);
                        break;
                    case 3:
                        mainBottom.setSelectedItemId(R.id.main_my);
                        setTitle(TITLE_NAME[3]);
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

        menu.add(0, NETWORK_ID, 0, R.string.network);
        menu.add(0, Exit_ID, 0, R.string.exit);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case NETWORK_ID:
                Intent netWork = new Intent(this, NetWorkActivity.class);
                startActivity(netWork);

                break;
            case Exit_ID:
                System.exit(0);

        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 双击返回键退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                this.finish();

            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                isExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void imageViewonClick(View v) {

        Toast.makeText(this, "你已经点击了图片", Toast.LENGTH_SHORT).show();
//        Intent netWork1=new Intent(this, SystemListActivity.class);
//        startActivity(netWork1);
        new AlertDialog.Builder(this)
                .setTitle("系统")
                .setItems(
                        R.array.Pos_items,
                        (dialog, which) -> {
                            if (which == 0) {//新增

                                Intent intent = new Intent(this, SystemAddActivity.class); //参数1:Fragment所依存的Activity,参数2：要跳转的Activity


                                Bundle bundle = new Bundle();
                                TextView one = findViewById(R.id.textViewSBid);
                                String id = one.getText().toString();
                                bundle.putString("id", id);
                                intent.putExtras(bundle);
                                startActivity(intent); //这里一定要获取到所在Activity再startActivity()；


                            } else if (which == 1) {// 修改
                                Intent intent = new Intent(this, SystemModActivity.class); //参数1:Fragment所依存的Activity,参数2：要跳转的Activity


                                Bundle bundle = new Bundle();
                                TextView one = findViewById(R.id.textViewSBid);
                                String id = one.getText().toString();
                                bundle.putString("id", id);
                                intent.putExtras(bundle);
                                startActivity(intent); //这里一定要获取到所在Activity再startActivity()；


                            } else {  //删除

                                ReqParam req = new ReqParam();
                                req.setUrl(ComDef.INTF_SYSDEL);//从INTF_SYSDEL接口删除数据
                                HashMap map1 = new HashMap<String, String>();

                                TextView one = findViewById(R.id.textViewSBid);
                                String id1 = one.getText().toString();


                                map1.put(ComDef.QUERY_SYSINDEX, id1); //获取需要的字段：sysName, editTextXTMCInput.getText().toString()); //获取需要的字段：sysName

                                req.setMap(map1);

                                new GetData(req) {
                                    @Override
                                    public void dealResult(String result) throws JSONException {
                                        MainActivity.this.runOnUiThread(() -> {
                                            ToastUtil.toastCenter(MainActivity.this, result);
                                        });
                                    }

                                };


                            }
                        }).show();


    }


}

