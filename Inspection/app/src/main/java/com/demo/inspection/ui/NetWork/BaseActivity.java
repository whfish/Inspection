package com.demo.inspection.ui.NetWork;

import android.content.Intent;
import android.content.IntentFilter;

import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.inspection.R;
import com.demo.inspection.dialog.MyAlertDialog;
import com.demo.inspection.receiver.NetBroadcastReceiver;
import com.demo.inspection.utils.NetUtils;
import com.demo.inspection.utils.ToastUtil;

/**
 * @ProjectName: Inspection
 * @Package: com.demo.inspection.ui
 * @ClassName: BaseActivity
 * @Description: java类作用描述
 * @Author: 杨秀娟
 * @CreateDate: 2019/11/4 17:34
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/4 17:34
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class BaseActivity extends AppCompatActivity implements NetBroadcastReceiver.NetEvevt {

    public static NetBroadcastReceiver.NetEvevt evevt;
    /**
     * 网络类型
     */
    private int netMobile;
    /*弹窗定义*/
    private MyAlertDialog alertDialog = null;
    /*建议提醒*/
    private static ToastUtil toastUtil;
    private NetBroadcastReceiver netBroadcastReceiver;
//    public static NetBroadcastReceiver.NetChangeListener listener;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        evevt = this;

        //Android 7.0以上需要动态注册
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //实例化IntentFilter对象
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            netBroadcastReceiver = new NetBroadcastReceiver();
            //注册广播接收
            registerReceiver(netBroadcastReceiver, filter);
        }
        inspectNet();
    }

    //实例

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub

        unregisterReceiver(netBroadcastReceiver);
        super.onDestroy();
    }

    /**
     * 初始化时判断有没有网络
     */

    public boolean inspectNet() {
        this.netMobile = NetUtils.getNetWorkState(BaseActivity.this);
        if (!isNetConnect()) {
            //网络异常，请检查网络
            showNetDialog();

        }
        return isNetConnect();
    }

    /**
     * 网络变化之后的类型
     */
    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        if (!isNetConnect()) {
            showNetDialog();
//           toastUtil.toastCenter(this, getString(R.string.nonetwork));
        } else {
            hideNetDialog();
            toastUtil.toastCenter(this, getString(R.string.succnetwork));
        }

    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == NetUtils.NETWORK_WIFI) {
            return true;
        } else if (netMobile == NetUtils.NETWORK_MOBILE) {
            return true;
        } else if (netMobile == NetUtils.NETWORK_NONE) {
            return false;

        }
        return false;
    }

    /**
     * 隐藏设置网络框
     */
    private void hideNetDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        alertDialog = null;
    }

    /**
     * 弹出设置网络框
     */
    private void showNetDialog() {
        if (alertDialog == null) {
            alertDialog = new MyAlertDialog(this).builder().setTitle("网络异常")
                    .setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setPositiveButton("设置", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            startActivity(intent);
                        }
                    }).setCancelable(false);
        }
        alertDialog.show();
//        showMsg("网络异常，请检查网络");
    }
}

