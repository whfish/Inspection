package com.demo.inspection.bl;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @ProjectName: Inspection
 * @Package: com.demo.inspection.bl
 * @ClassName: MyFragmentPagerAdapter
 * @Description: java类作用描述
 * @Author: 王欢
 * @CreateDate: 2019/11/4 16:14
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/4 16:14
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    public MyFragmentPagerAdapter(List<Fragment> fragmentList, FragmentManager fm) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList != null && !fragmentList.isEmpty() ? fragmentList.size() : 0;
    }
}
