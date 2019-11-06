package com.demo.inspection.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.demo.inspection.ui.NetWork.BaseActivity;
import com.demo.inspection.utils.NetUtils;


public class NetBroadcastReceiver extends BroadcastReceiver {
    public NetEvevt evevt = BaseActivity.evevt;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                int netWorkState = NetUtils.getNetWorkState(context);
                if(evevt !=null)
                    // 接口回调传过去状态的类型
                    evevt.onNetChange(netWorkState);
        }
    }


    // 自定义接口
    public interface NetEvevt {
        public void onNetChange(int netMobile);
    }

    //实例



}
