package com.demo.inspection.ui.customview;

import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.demo.inspection.utils.ComDef;

/**
 * @ProjectName: Inspection
 * @Package: com.demo.inspection.bl
 * @ClassName: MyViewBinder
 * @Description: java类作用描述
 * @Author: 王欢
 * @CreateDate: 2019/11/8 10:53
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/8 10:53
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MyViewBinder implements SimpleAdapter.ViewBinder {
    @Override
    public boolean setViewValue(View view, Object o, String s) {
        if (o != null && o.equals("正常")) {
            TextView tv = (TextView)view;
            tv.setTextColor(ComDef.STATE_COLORS[1]);
        } else if (o != null && o.equals("预警")) {
            TextView tv = (TextView)view;
            tv.setTextColor(ComDef.STATE_COLORS[2]);
        }
        else if (o != null && o.equals("告警")) {
            TextView tv = (TextView)view;
            tv.setTextColor(ComDef.STATE_COLORS[3]);
        }else if (o != null && o.equals("异常")) {
            TextView tv = (TextView)view;
            tv.setTextColor(ComDef.STATE_COLORS[4]);
        }
        return false;
    }
}
