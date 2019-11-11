package com.demo.inspection.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.fragment.app.Fragment;

/**
 * @ProjectName: Inspection
 * @Package: com.demo.inspection.utils
 * @ClassName: ScreenUtil
 * @Description: java类作用描述
 * @Author: 杨秀娟
 * @CreateDate: 2019/11/5 14:29
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/5 14:29
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ScreenUtil {

    public void adapterScreen(Activity activity, int targetDP,Boolean isVertical) {
        //系统的屏幕尺寸
        DisplayMetrics systemDM = Resources.getSystem().getDisplayMetrics();
        //app整体的屏幕尺寸
        Object appDM = activity.getApplication().getResources().getDisplayMetrics();
        //activity的屏幕尺寸
        DisplayMetrics activityDM = activity.getResources().getDisplayMetrics();

        if (isVertical) {
            // 适配屏幕的高度
            activityDM.density = activityDM.heightPixels / targetDP;
        } else {
            // 适配屏幕的宽度
            activityDM.density = activityDM.widthPixels / targetDP;
        }
        // 适配相应比例的字体大小
        activityDM.scaledDensity = activityDM.density * (systemDM.scaledDensity / systemDM.density);
        // 适配dpi
        activityDM.densityDpi = (int) (160 * activityDM.density);
    }

    public void resetScreen(Activity activity ) {
        //系统的屏幕尺寸
        DisplayMetrics systemDM = Resources.getSystem().getDisplayMetrics();
        //app整体的屏幕尺寸
        DisplayMetrics appDM = activity.getApplication().getResources().getDisplayMetrics();
        //activity的屏幕尺寸
        DisplayMetrics activityDM = activity.getResources().getDisplayMetrics();

        activityDM.density = systemDM.density;
        activityDM.scaledDensity = systemDM.scaledDensity;
        activityDM.densityDpi = systemDM.densityDpi;

        appDM.density = systemDM.density;
        appDM.scaledDensity = systemDM.scaledDensity;
        appDM.densityDpi = systemDM.densityDpi;
    }

    public  int getScreenDPI(Activity mActivity) {
    DisplayMetrics metric =new DisplayMetrics();
    mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
    int dpi = (int) (metric.densityDpi*1.2);
    return dpi;
  }

    public void adapterScreen(Fragment fragment, int targetDP, Boolean isVertical) {
        //系统的屏幕尺寸
        DisplayMetrics systemDM = Resources.getSystem().getDisplayMetrics();
        //app整体的屏幕尺寸
        Object appDM = fragment.getContext().getApplicationContext().getResources().getDisplayMetrics();
        //activity的屏幕尺寸
        DisplayMetrics activityDM = fragment.getResources().getDisplayMetrics();

        if (isVertical) {
            // 适配屏幕的高度
            activityDM.density = activityDM.heightPixels / targetDP;
        } else {
            // 适配屏幕的宽度
            activityDM.density = activityDM.widthPixels / targetDP;
        }
        // 适配相应比例的字体大小
        activityDM.scaledDensity = activityDM.density * (systemDM.scaledDensity / systemDM.density);
        // 适配dpi
        activityDM.densityDpi = (int) (160 * activityDM.density);
    }

    public void resetScreen(Fragment fragment ) {
        //系统的屏幕尺寸
        DisplayMetrics systemDM = Resources.getSystem().getDisplayMetrics();
        //app整体的屏幕尺寸
        DisplayMetrics appDM = fragment.getContext().getApplicationContext().getResources().getDisplayMetrics();
        //activity的屏幕尺寸
        DisplayMetrics activityDM = fragment.getResources().getDisplayMetrics();

        activityDM.density = systemDM.density;
        activityDM.scaledDensity = systemDM.scaledDensity;
        activityDM.densityDpi = systemDM.densityDpi;

        appDM.density = systemDM.density;
        appDM.scaledDensity = systemDM.scaledDensity;
        appDM.densityDpi = systemDM.densityDpi;
    }

    public  int getScreenDPI(Fragment fragment) {
        DisplayMetrics metric =fragment.getContext().getApplicationContext().getResources().getDisplayMetrics();
//           int dp =metric.widthPixels;
        int dp = metric.heightPixels;
//        int dpi = (int) (metric.densityDpi*1.2);
//        return dpi;
        return dp;
    }


}
