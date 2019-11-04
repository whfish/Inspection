package com.demo.inspection.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.demo.inspection.R;
import com.demo.inspection.utils.NetworkInformation;

/**
 * @ProjectName: Inspection
 * @Package: com.demo.inspection.ui
 * @ClassName: NetWorkActivity
 * @Description: java类作用描述
 * @Author: 杨秀娟
 * @CreateDate: 2019/11/4 17:33
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/4 17:33
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class NetWorkActivity extends BaseActivity {
    private NetworkInformation networkInformation
            =new NetworkInformation();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network);
        TextView textView = findViewById(R.id.netconnect);
        TextView nettype = findViewById(R.id.nettype);
        TextView ipaddress = findViewById(R.id.ipaddress);
        networkInformation.setContext(this);

        if (networkInformation.getNetworkType() == null) {
            textView.setText(R.string.nonetwork);
        } else {
            textView.setText(R.string.succnetwork);
            nettype.setText("网络连接类型:" + networkInformation.getNetworkType());
            ipaddress.setText("网络ip地址:" + networkInformation.getIPAddress());
        }

    }



}

