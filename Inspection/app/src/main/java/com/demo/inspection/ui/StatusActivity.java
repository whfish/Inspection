package com.demo.inspection.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.demo.inspection.R;
import com.demo.inspection.ui.Fragment.EquipmentFragment;
import com.demo.inspection.ui.Fragment.MeFragment;
import com.demo.inspection.ui.Fragment.StatusFragment;
import com.demo.inspection.ui.Fragment.SystemFragment;

import java.util.ArrayList;
import java.util.List;

import static com.demo.inspection.bl.ComDef.TITLE_NAME;

public class StatusActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private RadioGroup mTabRadioGroup;

    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_as);
        initView ();
    }

    private void initView() {
        // find view
        mViewPager = findViewById (R.id.fragment_container);
        mTabRadioGroup = findViewById (R.id.tabs);
        // init fragment
        mFragments = new ArrayList<> (4);
        mFragments.add (StatusFragment.getInstances ("状态"));
        mFragments.add (EquipmentFragment.getInstances ("设备"));
        mFragments.add (SystemFragment.getInstances ("系统"));
        mFragments.add (MeFragment.getInstances ("我的"));
        // init view pager
        mAdapter = new MyFragmentPagerAdapter (getSupportFragmentManager (), mFragments);
        mViewPager.setAdapter (mAdapter);
        // register listener
        mViewPager.addOnPageChangeListener (mPageChangeListener);
        mTabRadioGroup.setOnCheckedChangeListener (mOnCheckedChangeListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        mViewPager.removeOnPageChangeListener (mPageChangeListener);
    }


    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener () {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton) mTabRadioGroup.getChildAt (position);
            radioButton.setChecked (true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener () {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i = 0; i < group.getChildCount (); i++) {
                if (group.getChildAt (i).getId () == checkedId) {
                    mViewPager.setCurrentItem (i);
                    setTitle (TITLE_NAME[i]);
                    return;
                }
            }
        }
    };

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super (fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get (position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0 : this.mList.size ();
        }
    }

}
